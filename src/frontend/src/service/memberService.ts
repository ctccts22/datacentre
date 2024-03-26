import axios from "@/plugin/axios.ts";
import {MemberSearchCondition} from "@/model/member.search.model.ts";
import {RegMember} from "@/model/member.register.model.ts";
import {PaginationModel} from "@/model/pagination.model.ts";

export async function memberSearch(memberSearchConditionDTO: MemberSearchCondition, pagination: PaginationModel) {
  console.log("pagination", pagination);
  console.log("memberSearchConditionDTO", memberSearchConditionDTO);

  // pageable naming*
  const params = {
    page: pagination.page,
    size: pagination.size,
    sort: `${pagination.sort},${pagination.direction}`,
    ...memberSearchConditionDTO
  };
  console.log("params", params);

  return await axios.get('/member/member-management', { params });
}

export async function registerMember(registerMemberDTO: RegMember) {
  return await axios.post('/member/registration', registerMemberDTO);
}