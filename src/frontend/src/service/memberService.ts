import axios from "@/plugin/axios.ts";
import {MemberSearchCondition} from "@/model/member.search.model.ts";
import {RegMember} from "@/model/member.register.model.ts";

export async function memberSearch(memberSearchConditionDTO: MemberSearchCondition) {
  return await axios.get('/member/member-management', { params: memberSearchConditionDTO })
}

export async function registerMember(registerMemberDTO: RegMember) {
  return await axios.post('/member/registration', registerMemberDTO);
}