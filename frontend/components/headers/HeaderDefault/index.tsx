import React from "react";

import { Button } from "@/components/ui/button";
import { Home, User } from "lucide-react";
import Link from "next/link";

const HeaderDefault = () => {
    return (
        <header className="border-b bg-white">
            <div className="px-4 mx-auto max-w-7xl flex items-center justify-between py-4">
                <Link href="/" className="flex items-center gap-2">
                    <Home className="h-6 w-6 text-[#e56b4e]" />
                    <span className="text-xl font-heading font-semibold">KitNet</span>
                </Link>
                <nav className="hidden md:flex items-center gap-6">
                    <Link href="/properties" className="text-foreground/80 hover:text-primary transition-colors">
                        Propriedades
                    </Link>
                    <Link href="/add-property" className="text-foreground/80 hover:text-primary transition-colors">
                        Anunciar
                    </Link>
                    <Link href="/about" className="text-foreground/80 hover:text-primary transition-colors">
                        Sobre
                    </Link>
                    <Link href="/contact" className="text-foreground/80 hover:text-primary transition-colors">
                        Contato
                    </Link>
                </nav>
                <div className="flex items-center gap-2">
                    <Link href="/login">
                        <Button variant="outline" className="hidden sm:flex items-center gap-2">
                            <User className="h-4 w-4" />
                            Entrar
                        </Button>
                    </Link>
                    <Link href="/register">
                        <Button className="bg-[#e56b4e] text-white hover:bg-[#e56b4e]/90">Registrar</Button>
                    </Link>
                </div>
            </div>
        </header>
    );
};

export default HeaderDefault;