export const PASSWORD_RULE = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^A-Za-z0-9]).{8}$/;

export function suggestUsername(firstName: string, surname: string): string {
  const raw = `${firstName}${surname}`.replace(/[^A-Za-z0-9]/g, '');
  return raw.slice(0, 10);
}
