export class Login {
  public id: string;
  public passwd: string;

  constructor(id?: string, passwd?: string) {
    this.id = id || '';
    this.passwd = passwd || '';
  }
}