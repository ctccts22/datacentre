import {defineStore} from 'pinia';
import * as memberService from '@/service/memberService.ts';
import {MemberSearchCondition} from "@/model/member.search.model.ts";
import {RegMember} from "@/model/member.register.model.ts";
import {PaginationModel} from "@/model/pagination.model.ts";
import {MemberModel} from "@/model/member.model.ts";
import {UpdateMember} from "@/model/member.update.model.ts";

export const useMemberStore = defineStore('member', {
  state: () => ({
    memberList: [] as MemberModel[],
    regMember: new RegMember(),
    updateMember: new UpdateMember(),
    memberCondition: new MemberSearchCondition(),
    pagination: new PaginationModel(),
    selectedUsername: '',
    modalStatus: false,
    role: [
      {value: '', label: '--'},
      {value: 'ADMIN', label: 'Admin'},
      {value: 'USER', label: 'User'}
    ],
    status: [
      {value: '', label: '--'},
      {value: 'APPROVED', label: 'Active'},
      {value: 'DELETED', label: 'Inactive'}
    ],
    totalElements: 0,
    totalPages: 0
  }),
  actions: {
    setModalStatus(status: boolean) {
      this.modalStatus = status;
    },
    setSelectedUsername(username: string) {
      this.selectedUsername = username;
    },
    async registerMember(registerMemberDTO: RegMember) {
      try {
        await memberService.registerMember(registerMemberDTO);
      } catch (e) {
        console.error('Error during login:', e);
        throw e;
      }
    },
    async updateMember(updateMemberDTO: UpdateMember) {
      try {
        await memberService.updateMember(updateMemberDTO);
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