export class Member {
  public id: string;
  public level: string | number;

  constructor(id?: string, level?: string | number) {
    this.id = id || '';
    this.level = level || '';
  }
}