import { defineStore } from 'pinia';
import * as memberService from '@/service/memberService.ts';
import {MemberSearchCondition} from "@/model/member.search.model.ts";
import {RegMember} from "@/model/member.register.model.ts";
export const useMemberStore = defineStore('member', {
  state: () => ({
    regMember: new RegMember(),
    memberCondition: new MemberSearchCondition(),
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
       const response = await memberService.memberSearch(memberSearchConditionDTO);
       this.memberCondition = response.data;
     } catch (e) {
       console.error('Error during login:', e);
       throw e;
     }
    }
  }
})