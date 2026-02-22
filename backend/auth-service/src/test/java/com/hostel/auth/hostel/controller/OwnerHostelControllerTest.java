package com.hostel.auth.hostel.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hostel.auth.exception.ForbiddenException;
import com.hostel.auth.hostel.service.OwnerHostelService;
import com.hostel.auth.security.OwnerAuthContext;
import com.hostel.auth.security.OwnerAuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OwnerHostelController.class)
class OwnerHostelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerHostelService ownerHostelService;

    @MockBean
    private OwnerAuthService ownerAuthService;

    @Test
    void deleteHostelReturnsNoContentForOwnedHostel() throws Exception {
        when(ownerAuthService.requireOwner(anyString())).thenReturn(new OwnerAuthContext(7L, "owner", "OWNER"));

        mockMvc.perform(delete("/api/owner/hostels/11")
                .header("Authorization", "Bearer token"))
            .andExpect(status().isNoContent());

        verify(ownerHostelService).delete(7L, 11L);
    }

    @Test
    void deleteHostelReturnsForbiddenForDifferentOwner() throws Exception {
        when(ownerAuthService.requireOwner(anyString())).thenReturn(new OwnerAuthContext(7L, "owner", "OWNER"));
        doThrow(new ForbiddenException("hostel not found for this owner"))
            .when(ownerHostelService).delete(7L, 99L);

        mockMvc.perform(delete("/api/owner/hostels/99")
                .header("Authorization", "Bearer token"))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.message").value("hostel not found for this owner"));
    }

    @Test
    void hostelsExistReturnsBooleanFlag() throws Exception {
        when(ownerAuthService.requireOwner(anyString())).thenReturn(new OwnerAuthContext(7L, "owner", "OWNER"));
        when(ownerHostelService.ownerHasHostels(7L)).thenReturn(true);

        mockMvc.perform(get("/api/owner/hostels/exists")
                .header("Authorization", "Bearer token"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.exists").value(true));
    }
}
