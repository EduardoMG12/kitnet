import { create } from "zustand";

export type HeaderType =
	| "admin"
	| "support"
	| "lessee"
	| "lessor"
	| "realStateAgent"
	| "withoutLogin"
	| null;

interface HeaderState {
	currentHeader: HeaderType;
	setHeader: (header: HeaderType) => void;
	resetHeader: () => void;
}

export const useHeaderStore = create<HeaderState>((set) => ({
	currentHeader: "withoutLogin",
	setHeader: (header) => set({ currentHeader: header }),
	resetHeader: () => set({ currentHeader: null }),
}));
