import { defineStore } from 'pinia';
import {RegMember} from "@/model/regMember.model.ts";
import * as memberService from '@/service/memberService.ts';
import {MemberSearchCondition} from "@/model/member.search.model.ts";
export const useMemberStore = defineStore('member', {
  state: () => ({
    regMember: new RegMember(),
    MemberSearchCondition: new MemberSearchCondition(),
  }),
  actions: {
    async registerMember(registerMemberDTO: RegMember) {
      try {
        await memberService.registerMember(registerMemberDTO);
      } catch (e) {
        console.error('Error during login:', e);
        throw e;
      }
    },
    async memberSearch(memberSearchConditionDTO: MemberSearchCondition) {
     try {
       await memberService.memberSearch(memberSearchConditionDTO);
     } catch (e) {
       console.error('Error during login:', e);
       throw e;
     }
    }
  }
})