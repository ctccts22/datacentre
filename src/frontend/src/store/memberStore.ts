import { defineStore } from 'pinia';
import {RegMember} from "@/model/regMember.model.ts";
import * as memberService from '@/service/memberService.ts';
export const useMemberStore = defineStore('member', {
  state: () => ({
    member: new RegMember(),
  }),
  actions: {
    async registerMember(registerMemberDTO: RegMember) {
      try {
        await memberService.registerMember(registerMemberDTO);
      } catch (e) {
        console.error('Error during login:', e);
        throw e;
      }
    }
  }
})