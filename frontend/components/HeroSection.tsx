import React from 'react';
import { Button } from "@/components/ui/button";
import { Search } from "lucide-react";
import { Input } from "@/components/ui/input";
import Link from 'next/link';

const HeroSection = () => {
    return (
        <div className="bg-[#f9f4e8] py-16 md:py-24">
            <div className="px-4 mx-auto max-w-7xl">
                <div className="grid grid-cols-1 md:grid-cols-2 gap-12 items-center">
                    <div>
                        <h1 className="text-4xl md:text-5xl lg:text-6xl font-heading font-bold text-[#2c3e50] mb-6">
                            Encontre o imóvel <span className="text-[#e56b4e]">perfeito</span> para você
                        </h1>
                        <p className="text-lg text-[#2c3e50]/80 mb-8">
                            Conectando proprietários e inquilinos de forma simples e segura. Seu próximo lar está a apenas alguns cliques de distância.
                        </p>

                        <div className="relative">
                            <Input
                                placeholder="Busque por cidade, bairro ou tipo de imóvel..."
                                className="pl-4 pr-12 py-6 rounded-full shadow-md"
                            />
                            <Button
                                className="absolute right-1.5 top-1/2 transform -translate-y-1/2 rounded-full px-3 bg-[#e56b4e] hover:bg-[#e56b4e]/90"
                            >
                                <Search className="h-5 w-5" />
                            </Button>
                        </div>

                        <div className="flex flex-wrap gap-2 mt-6">
                            <Link href="/properties?type=apartment">
                                <Button variant="outline">Apartamentos</Button>
                            </Link>
                            <Link href="/properties?type=house">
                                <Button variant="outline">Casas</Button>
                            </Link>
                            <Link href="/properties?type=commercial">
                                <Button variant="outline">Comercial</Button>
                            </Link>
                            <Link href="/properties?type=land">
                                <Button variant="outline">Terrenos</Button>
                            </Link>
                        </div>
                    </div>

                    <div className="hidden md:block relative">
                        <img
                            src="https://images.unsplash.com/photo-1487958449943-2429e8be8625?auhref=format&fit=crop&w=600&h=700&q=80"
                            alt="KitNet"
                            className="rounded-2xl shadow-xl object-cover h-[500px] w-full"
                        />
                        <div className="absolute -bottom-8 -left-8 bg-white p-4 rounded-xl shadow-lg max-w-xs">
                            <div className="flex items-center mb-2">
                                <div className="h-3 w-3 rounded-full bg-[#e56b4e] mr-2" />
                                <p className="font-medium text-sm">Imóveis verificados</p>
                            </div>
                            <p className="text-sm text-muted-foreground">
                                Todos os imóveis são verificados pela nossa equipe para garantir qualidade e segurança.
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default HeroSection;