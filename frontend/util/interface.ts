interface typeJwtDecode {
	header: { alg: string };
	payload: {
		roles:
			| "LESSEE"
			| "LESSOR"
			| "REAL_STATE_AGENT"
			| "ADMIN"
			| "MODERATOR"
			| "SUPPORT"
			| undefined;
		userId: string;
		sub: string;
		iat: number;
		exp: number;
	};
}
