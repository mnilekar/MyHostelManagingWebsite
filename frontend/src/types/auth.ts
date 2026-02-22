export type Gender = 'MALE' | 'FEMALE' | 'NOT_PREFERRED';
export type UserRole = 'USER' | 'EMPLOYEE' | 'OWNER';

export type AuthUser = {
  name: string;
  username: string;
  email: string;
  role: UserRole;
};

export type LoginResponse = {
  message: string;
  token: string;
  user: AuthUser;
};

export type RegisterBaseRequest = {
  firstName: string;
  middleName?: string;
  surname?: string;
  gender?: Gender;
  email: string;
  nationality: string;
  countryCode: string;
  mobileNumber: string;
  stateName: string;
  cityName: string;
  idProofType: 'AADHAR_CARD' | 'VOTER_ID' | 'PASSPORT';
  idProofValue: string;
  username: string;
  password: string;
  confirmPassword: string;
};

export type RegisterUserRequest = RegisterBaseRequest & { homeAddress?: string };

export type RegisterEmployeeRequest = RegisterBaseRequest & {
  permanentAddress: string;
  currentAddress: string;
  skill: 'Cleaning' | 'Management';
};

export type RegisterOwnerRequest = RegisterBaseRequest & {
  permanentAddress: string;
  currentAddress: string;
};
