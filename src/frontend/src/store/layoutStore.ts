import { defineStore } from 'pinia';

export const layoutStore = defineStore('commonStore', {
  state: () => ({
    sideBarOpen: false,
  }),

  getters: {
    sideBarOpen(state) {
      return state.sideBarOpen
    },
  },
});

