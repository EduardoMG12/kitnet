import { redirect } from "next/navigation";
import { MiddlewareConfig, NextRequest, NextResponse } from "next/server";
import { jwtDecode } from "./util/jwt-decode";

const publicRoutes = [
	{ path: "/", whenAuthenticated: "next" },
	{ path: "/errors", whenAuthenticated: "next" },
	{ path: "/examples", whenAuthenticated: "next" },
	{ path: "/sign-in", whenAuthenticated: "redirect" },
	{ path: "/sign-up", whenAuthenticated: "redirect" },
	{ path: "/avalible-properties", whenAuthenticated: "next" },
	{ path: "/documentation", whenAuthenticated: "next" },
	{ path: "/properties", whenAuthenticated: "next" },
	{ path: "/forgot-password", whenAuthenticated: "redirect" },
	{ path: "/reset-password", whenAuthenticated: "redirect" },
	{ path: "/guides", whenAuthenticated: "next" },
	// "/email-confirm" , whenAuthenticated: "next" },
] as const;

const roleMappings = {
	admin: "ADMIN",
	moderator: "MODERATOR",
	support: "SUPPORT",
	"real-state-agent": "REAL_STATE_AGENT",
	lessor: "LESSOR",
	lessee: "LESSEE",
} as const;

const REDIRECT_WHEN_NOT_AUTHENTICATED_ROUTE = "/sign-in";

export function middleware(request: NextRequest) {
	const path = request.nextUrl.pathname;
	const publicRoute = publicRoutes.find((route) => route.path === path);
	const authToken = request.cookies.get("authToken")?.value;

	if (!authToken && publicRoute) {
		return NextResponse.next();
	}

	if (!authToken && !publicRoute) {
		const redirectUrl = request.nextUrl.clone();
		redirectUrl.pathname = REDIRECT_WHEN_NOT_AUTHENTICATED_ROUTE;

		return NextResponse.redirect(redirectUrl);
	}

	if (authToken && publicRoute?.whenAuthenticated === "redirect") {
		const redirectUrl = request.nextUrl.clone();
		redirectUrl.pathname = "/";

		return NextResponse.redirect(redirectUrl);
	}

	if (authToken && !publicRoute) {
		try {
			const decodedToken = jwtDecode<typeJwtDecode>(authToken) as typeJwtDecode;

			if (decodedToken?.payload.exp * 1000 > Date.now()) {
				const redirectUrl = request.nextUrl.clone();
				redirectUrl.pathname = REDIRECT_WHEN_NOT_AUTHENTICATED_ROUTE;

				return NextResponse.redirect(redirectUrl);
			}

			const userRoles = decodedToken?.payload.roles || ([] as string[]);
			const requestedPath = request.nextUrl.pathname;

			const pathKeys = Object.keys(roleMappings);
			const requiredRole = pathKeys.find(
				(key): key is keyof typeof roleMappings => requestedPath.includes(key),
			);

			if (requiredRole && !userRoles.includes(roleMappings[requiredRole])) {
				const redirectUrl = request.nextUrl.clone();
				redirectUrl.pathname = "/forbidden";
				return NextResponse.redirect(redirectUrl);
			}
			return NextResponse.next();
		} catch (error) {
			console.error("Erro ao decodificar o token:", error);
			const redirectUrl = request.nextUrl.clone();
			redirectUrl.pathname = REDIRECT_WHEN_NOT_AUTHENTICATED_ROUTE;
			return NextResponse.redirect(redirectUrl);
		}
	}
	// if (request.nextUrl.pathname.includes("")) { //without role
	// }
	// if (request.nextUrl.pathname.includes("admin")) {
	// }
	// if (request.url.includes("moderator")) {
	// }
	// if (request.url.includes("support")) {
	// }
	// if (request.url.includes("real-state-agent")) {
	// }
	// if (request.url.includes("lessor")) {
	// }
	// if (request.url.includes("lessee")) {
	// }
}

export const config: MiddlewareConfig = {
	matcher: ["/((?!api|_next/static|_next/image|favicon.ico).*)"],
};

// criar a validacao no FormDataEvent, metade do video para FragmentDirective

// // https://www.youtube.com/watch?v=nlc-l2nW_J0
