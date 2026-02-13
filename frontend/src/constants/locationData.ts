export type LocationMaster = {
  countryCode: string;
  states: Record<string, string[]>;
};

// Placeholder for future DB-driven values.
export const LOCATION_DATA: Record<string, LocationMaster> = {
  India: {
    countryCode: '+91',
    states: {
      Maharashtra: ['Pune', 'Mumbai', 'Nagpur']
    }
  },
  Nepal: {
    countryCode: '+977',
    states: {
      Bagmati: ['Kathmandu']
    }
  }
};
