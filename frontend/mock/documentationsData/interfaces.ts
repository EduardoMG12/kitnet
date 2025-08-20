export interface Developer {
	name: string;
	linkedin: string;
	github: string;
	role: string;
	description: string;
}

export interface TechStackItem {
	name: string;
	description: string;
	icon: React.ReactNode;
}

export interface Professor {
	name: string;
	linkedin: string;
	subject: string;
	description: string;
}

export interface Route {
	path: string;
	beforePathNotVisible?: string;
	afterPathNotVisible?: string;
	name: string;
	description: string;
	implemented: boolean;
	needsAuth: boolean;
	espeficicRole: string[];
}

export interface RouteCategory {
	category: string;
	icon: React.ReactNode;
	routes: Route[];
}
