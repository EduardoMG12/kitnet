import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";

export function middleware(request: NextRequest) {
	const isAuthenticated = request.cookies.has("jwtToken");
	const isAdminRoute = request.nextUrl.pathname.startsWith("/admin");

	if (isAdminRoute && !isAuthenticated) {
		return NextResponse.redirect(new URL("/errors/access-denied", request.url));
	}
	return NextResponse.next();
}
export const config = {
	matcher: ["/admin/:path*"], // Aplica o middleware a rotas /admin
};
