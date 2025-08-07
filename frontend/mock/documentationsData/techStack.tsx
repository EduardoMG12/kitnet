import {
    Code,
    Database,
    Server,
    Globe,
    Shield,
    Triangle,
} from "lucide-react";
import type { TechStackItem } from "./interfaces";

export const techStack: TechStackItem[] = [
    {
        name: "React",
        icon: <Code className="h-5 w-5" />,
        description: "Framework frontend",
    },
    {
        name: "TypeScript",
        icon: <Code className="h-5 w-5" />,
        description: "Linguagem tipada",
    },
    {
        name: "Tailwind CSS",
        icon: <Globe className="h-5 w-5" />,
        description: "Framework CSS",
    },
    {
        name: "Next",
        icon: <Triangle fill="#e56b4e" className="h-5 w-5" />,
        description: "Build tool",
    },
    {
        name: "Spring Boot",
        icon: <Server className="h-5 w-5" />,
        description: "Backend Java",
    },
    {
        name: "MySQL",
        icon: <Database className="h-5 w-5" />,
        description: "Banco de dados",
    },
    {
        name: "Docker",
        icon: <Server className="h-5 w-5" />,
        description: "Containerização",
    },
    {
        name: "Firebase Auth",
        icon: <Shield className="h-5 w-5" />,
        description: "Autenticação",
    },
];