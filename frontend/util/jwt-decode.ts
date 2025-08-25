export function jwtDecode<F>(token: string): F | null {
	try {
		const parts = token.split(".");
		if (parts.length !== 3) {
			throw new Error("Token inválido: formato incorreto.");
		}
		const base64Url = parts[1];
		const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
		const jsonPayload = decodeURIComponent(
			atob(base64)
				.split("")
				.map((c) => `%${(`00${c.charCodeAt(0).toString(16)}`).slice(-2)}`)
				.join(""),
		);
		return JSON.parse(jsonPayload) as F;
	} catch (e) {
		console.error("Erro na decodificação do JWT:", e);
		return null;
	}
}
