export class MemberSearchCondition {
  public name: string;
  public role: string;
  public status: string;
  public rdateStart: string;
  public rdateEnd: string;

  // For Pageable
  public pageNumber: number;
  public pageSize: number;
  public sort: string;
  public direction: string;

  constructor(
    name?: string,
    role?: string,
    status?: string,
    rdateStart?: string,
    rdateEnd?: string,
    pageNumber?: number,
    pageSize?: number,
    sort?: string,
    direction?: string,
  ) {
    this.name = name || '';
    this.role = role || '';
    this.status = status || '';
    this.rdateStart = rdateStart || '';
    this.rdateEnd = rdateEnd || '';
    this.pageNumber = pageNumber || 0;
    this.pageSize = pageSize || 10;
    this.sort = sort || 'username';
    this.direction = direction || 'asc';
  }
}