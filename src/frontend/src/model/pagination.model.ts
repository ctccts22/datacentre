export class PaginationModel {
  public page: number;
  public size: number;
  public sort: string;
  public direction: string;
  constructor(
    page?: number,
    size?: number,
    sort?: string,
    direction?: string,
  ) {
    this.page = page || 0;
    this.size = size || 5;
    this.sort = sort || 'username';
    this.direction = direction || 'asc';
  }
}
