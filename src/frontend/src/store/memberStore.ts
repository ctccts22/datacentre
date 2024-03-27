import { defineStore } from 'pinia';
import * as memberService from '@/service/memberService.ts';
import {MemberSearchCondition} from "@/model/member.search.model.ts";
import {RegMember} from "@/model/member.register.model.ts";
import {PaginationModel} from "@/model/pagination.model.ts";
import {MemberModel} from "@/model/member.model.ts";

export const useMemberStore = defineStore('member', {
  state: () => ({
    memberList: [] as MemberModel[],
    regMember: new RegMember(),
    memberCondition: new MemberSearchCondition(),
    pagination: new PaginationModel(),
    role: [
      { value: '', label: '--' },
      { value: 'ADMIN', label: 'Admin' },
      { value: 'USER', label: 'User' }
    ],
    status: [
      { value: '', label: '--' },
      { value: 'APPROVED', label: 'Active' },
      { value: 'DELETED', label: 'Inactive' }
    ],
    totalElements: 0,
    totalPages: 0
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
    async memberSearch() {
     try {
       console.log('memberSearch called with:', this.memberCondition, this.pagination);
       const response = await memberService.memberSearch(this.memberCondition, this.pagination);
       console.log('memberSearch response:', response);
       this.memberList = response.data.content;
       this.totalElements = response.data.totalElements;
       this.totalPages = response.data.totalPages;
     } catch (e) {
       console.error('Error during login:', e);
       throw e;
     }
    }
  }
})