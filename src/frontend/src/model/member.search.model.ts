export class MemberSearchCondition {
  public username: string;
  public email: string;
  public name: string;
  public role: string;
  public status: string;
  public rdateStart: string;
  public rdateEnd: string;

  constructor(
    username?: string,
    email?: string,
    name?: string,
    role?: string,
    status?: string,
    rdateStart?: string,
    rdateEnd?: string,
  ) {
    this.username = username || '';
    this.email = email || '';
    this.name = name || '';
    this.role = role || '';
    this.status = status || '';
    this.rdateStart = rdateStart || '';
    this.rdateEnd = rdateEnd || '';
  }
}