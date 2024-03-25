import axios from "@/plugin/axios.ts";
import {RegMember} from "@/model/regMember.model.ts";
export async function registerMember(registerMemberDTO: RegMember) {
  return await axios.post('/member/registration', registerMemberDTO);
}