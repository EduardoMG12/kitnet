import React from "react";
import { Home } from "lucide-react";
import Link from "next/link";

const Footer = () => {
    return (
        <footer className="bg-[#2c3e50] text-white mt-16">
            <div className="px-4 mx-auto max-w-7xl py-12">
                <div className="grid grid-cols-1 md:grid-cols-4 gap-8">
                    <div>
                        <Link href="/" className="flex items-center gap-2">
                            <Home className="h-6 w-6 text-[#e56b4e]" />
                            <span className="text-xl font-heading font-semibold">KitNet</span>
                        </Link>
                        <p className="mt-4 text-white/70">
                            Conectando proprietários e inquilinos com simplicidade e segurança.
                        </p>
                    </div>

                    <div>
                        <h3 className="font-heading text-lg font-medium mb-4">Navegação</h3>
                        <ul className="space-y-2">
                            <li>
                                <Link href="/" className="text-white/70 hover:text-white transition-colors">
                                    Home
                                </Link>
                            </li>
                            <li>
                                <Link href="/avalible-properties" className="text-white/70 hover:text-white transition-colors">
                                    Propriedades
                                </Link>
                            </li>
                            <li>
                                <Link href="/add-property" className="text-white/70 hover:text-white transition-colors">
                                    Anunciar
                                </Link>
                            </li>
                        </ul>
                    </div>

                    <div>
                        <h3 className="font-heading text-lg font-medium mb-4">Suporte</h3>
                        <ul className="space-y-2">
                            <li>
                                <Link href="/about" className="text-white/70 hover:text-white transition-colors">
                                    Sobre Nós
                                </Link>
                            </li>
                            <li>
                                <Link href="/contact" className="text-white/70 hover:text-white transition-colors">
                                    Contato
                                </Link>
                            </li>
                            <li>
                                <Link href="/faq" className="text-white/70 hover:text-white transition-colors">
                                    Perguntas Frequentes
                                </Link>
                            </li>
                        </ul>
                    </div>

                    <div>
                        <h3 className="font-heading text-lg font-medium mb-4">Legal</h3>
                        <ul className="space-y-2">
                            <li>
                                <Link href="/terms" className="text-white/70 hover:text-white transition-colors">
                                    Termos de Uso
                                </Link>
                            </li>
                            <li>
                                <Link href="/privacy" className="text-white/70 hover:text-white transition-colors">
                                    Política de Privacidade
                                </Link>
                            </li>
                            <li>
                                <Link href="/cookies" className="text-white/70 hover:text-white transition-colors">
                                    Política de Cookies
                                </Link>
                            </li>
                        </ul>
                    </div>
                </div>

                <div className="border-t border-white/20 mt-8 pt-8 flex flex-col md:flex-row justify-between items-center">
                    <p className="text-white/70 text-sm">
                        © 2025 KitNet. Todos os direitos reservados.
                    </p>
                    <div className="flex space-x-4 mt-4 md:mt-0">
                        <Link href="#" className="text-white/70 hover:text-white transition-colors">
                            Facebook
                        </Link>
                        <Link href="#" className="text-white/70 hover:text-white transition-colors">
                            Instagram
                        </Link>
                        <Link href="#" className="text-white/70 hover:text-white transition-colors">
                            Twitter
                        </Link>
                    </div>
                </div>
            </div>
        </footer>
    );
};

export default Footer;