export class UpdateMember {
  public username: string;
  public password: string;
  public checkPassword: string;
  public name: string;
  public role: string;
  public status: string;
  public ldate: string;

  constructor(
    username?: string,
    password?: string,
    checkPassword?: string,
    name?: string,
    role?: string,
    status?: string,
    ldate?: string,
  ) {
    this.username = username || '';
    this.password = password || '';
    this.checkPassword = checkPassword || '';
    this.name = name || '';
    this.role = role || '';
    this.status = status || '';
    this.ldate = ldate || '';
  }
}