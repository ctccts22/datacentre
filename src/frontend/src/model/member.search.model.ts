export class MemberSearchCondition {
  public name: string;
  public role: string;
  public status: string;
  public rdateStart: string;
  public rdateEnd: string;

  constructor(
    name?: string,
    role?: string,
    status?: string,
    rdateStart?: string,
    rdateEnd?: string,
  ) {
    this.name = name || '';
    this.role = role || '';
    this.status = status || '';
    this.rdateStart = rdateStart || '';
    this.rdateEnd = rdateEnd || '';
  }
}