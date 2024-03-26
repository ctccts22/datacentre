export class RegMember {
  public username: string;
  public password: string;
  public checkPassword: string;
  public email: string;
  public name: string;

  constructor(
    username?: string,
    password?: string,
    checkPassword?: string,
    email?: string,
    name?: string,
  ) {
    this.username = username || '';
    this.password = password || '';
    this.checkPassword = checkPassword || '';
    this.email = email || '';
    this.name = name || '';
  }
}