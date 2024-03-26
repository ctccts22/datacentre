export class MemberModel {
  public username: string;
  public name: string;
  public email: string;
  public role: string;
  public status: string;
  public rdate: string;
  public ldate: string;

  constructor(
    username?: string,
    name?: string,
    email?: string,
    role?: string,
    status?: string,
    rdate?: string,
    ldate?: string,
  ) {
    this.username = username || '';
    this.name = name || '';
    this.email = email || '';
    this.role = role || '';
    this.status = status || '';
    this.rdate = rdate || '';
    this.ldate = ldate || '';
  }
}