import React from "react";
import { Button } from "@/components/ui/button";
import { ArrowRight, Home, User, Shield, Building, Search } from "lucide-react";
import MainLayout from "@/components/layout/MainLayout";
import HeroSection from "@/components/HeroSection";
import Link from "next/link";

const WelcomePage = () => {
    return (
        <MainLayout>
            <HeroSection />

            {/* How it works section */}
            <section className="py-16 bg-white">
                <div className="px-4 mx-auto max-w-7xl">
                    <div className="text-center mb-12">
                        <h2 className="text-3xl font-heading font-bold mb-4">Como Funciona</h2>
                        <p className="text-lg text-muted-foreground max-w-2xl mx-auto">
                            Encontrar seu próximo lar ou anunciar seu imóvel nunca foi tão fácil.
                        </p>
                    </div>

                    <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
                        <div className="bg-[#f9f4e8] rounded-lg p-6 text-center">
                            <div className="bg-[#e56b4e]/10 h-16 w-16 rounded-full flex items-center justify-center mx-auto mb-4">
                                <Search className="h-8 w-8 text-[#e56b4e]" />
                            </div>
                            <h3 className="font-heading font-medium text-xl mb-2">Busque</h3>
                            <p className="text-muted-foreground">
                                Encontre o imóvel ideal usando nossos filtros de busca avançada.
                            </p>
                        </div>

                        <div className="bg-[#f9f4e8] rounded-lg p-6 text-center">
                            <div className="bg-[#e56b4e]/10 h-16 w-16 rounded-full flex items-center justify-center mx-auto mb-4">
                                <Building className="h-8 w-8 text-[#e56b4e]" />
                            </div>
                            <h3 className="font-heading font-medium text-xl mb-2">Visite</h3>
                            <p className="text-muted-foreground">
                                Agende visitas e conheça os imóveis pessoalmente ou por tour virtual.
                            </p>
                        </div>

                        <div className="bg-[#f9f4e8] rounded-lg p-6 text-center">
                            <div className="bg-[#e56b4e]/10 h-16 w-16 rounded-full flex items-center justify-center mx-auto mb-4">
                                <Home className="h-8 w-8 text-[#e56b4e]" />
                            </div>
                            <h3 className="font-heading font-medium text-xl mb-2">Alugue</h3>
                            <p className="text-muted-foreground">
                                Finalize o processo de locação de forma simples e segura.
                            </p>
                        </div>
                    </div>

                    <div className="mt-12 text-center">
                        <Link href="/properties">
                            <Button size="lg" className="bg-[#e56b4e] hover:bg-[#e56b4e]/90">
                                Explorar Imóveis
                                <ArrowRight className="ml-2 h-4 w-4" />
                            </Button>
                        </Link>
                    </div>
                </div>
            </section>

            {/* Featured properties */}
            <section className="py-16 bg-secondary/50">
                <div className="px-4 mx-auto max-w-7xl">
                    <div className="flex justify-between items-center mb-8">
                        <h2 className="text-3xl font-heading font-bold">Destaques</h2>
                        <Link
                            href="/properties"
                            className="text-[#e56b4e] flex items-center hover:underline"
                        >
                            Ver todos
                            <ArrowRight className="ml-1 h-4 w-4" />
                        </Link>
                    </div>

                    <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
                        {featuredProperties.map((property) => (
                            <div key={property.id} className="property-card group">
                                <div className="relative overflow-hidden h-48">
                                    <img
                                        src={property.image}
                                        alt={property.title}
                                        className="w-full h-full object-cover transition-transform duration-300 group-hover:scale-110"
                                    />
                                    <div className="absolute top-2 left-2">
                                        <span className="bg-[#e56b4e] text-white text-xs font-medium px-2 py-1 rounded">
                                            {property.type}
                                        </span>
                                    </div>
                                </div>
                                <div className="p-4">
                                    <h3 className="font-heading font-medium text-lg">{property.title}</h3>
                                    <div className="flex items-center mt-1 text-muted-foreground text-sm">
                                        <Home className="h-3.5 w-3.5 mr-1" />
                                        <span>{property.location}</span>
                                    </div>
                                    <div className="flex justify-between items-center mt-4">
                                        <p className="text-[#e56b4e] font-medium">
                                            R$ {property.price.toLocaleString('pt-BR')}
                                        </p>
                                        <Link
                                            href={`/properties/${property.id}`}
                                            className="text-xs bg-[#e56b4e]/10 hover:bg-[#e56b4e]/20 text-[#e56b4e] px-2 py-1 rounded transition-colors"
                                        >
                                            Detalhes
                                        </Link>
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>
            </section>

            {/* For landlords */}
            <section className="py-16 bg-white">
                <div className="px-4 mx-auto max-w-7xl">
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-12 items-center">
                        <div>
                            <h2 className="text-3xl font-heading font-bold mb-4">
                                Tem um imóvel para alugar?
                            </h2>
                            <p className="text-lg text-muted-foreground mb-6">
                                Anuncie seu imóvel e encontre o inquilino ideal de forma rápida e segura.
                                Oferecemos ferramentas para ajudar na gestão do seu imóvel.
                            </p>

                            <ul className="space-y-4 mb-8">
                                <li className="flex items-start">
                                    <div className="bg-[#e56b4e]/10 p-1 rounded mt-1 mr-3">
                                        <Shield className="h-5 w-5 text-[#e56b4e]" />
                                    </div>
                                    <div>
                                        <h4 className="font-medium">Inquilinos verificados</h4>
                                        <p className="text-sm text-muted-foreground">
                                            Todos os inquilinos passam por um processo de verificação.
                                        </p>
                                    </div>
                                </li>
                                <li className="flex items-start">
                                    <div className="bg-[#e56b4e]/10 p-1 rounded mt-1 mr-3">
                                        <User className="h-5 w-5 text-[#e56b4e]" />
                                    </div>
                                    <div>
                                        <h4 className="font-medium">Suporte personalizado</h4>
                                        <p className="text-sm text-muted-foreground">
                                            Nosso time está disponível para ajudar em todo o processo.
                                        </p>
                                    </div>
                                </li>
                            </ul>

                            <Link href="/add-property">
                                <Button className="bg-[#e56b4e] hover:bg-[#e56b4e]/90">
                                    Anunciar meu imóvel
                                </Button>
                            </Link>
                        </div>

                        <div className="rounded-lg overflow-hidden shadow-lg">
                            <img
                                src="https://images.unsplash.com/photo-1460925895917-afdab827c52f?auhref=format&fit=crop&w=600&h=400&q=80"
                                alt="Landlord"
                                className="w-full h-auto"
                            />
                        </div>
                    </div>
                </div>
            </section>

            {/* Testimonials */}
            <section className="py-16 bg-[#2c3e50] text-white">
                <div className="px-4 mx-auto max-w-7xl">
                    <div className="text-center mb-12">
                        <h2 className="text-3xl font-heading font-bold mb-4">
                            O que nossos clientes dizem
                        </h2>
                        <p className="text-white/80 max-w-2xl mx-auto">
                            Veja alguns depoimentos de pessoas que encontraram seu imóvel ideal na KitNet.
                        </p>
                    </div>

                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                        <div className="bg-[#2c3e50]/50 border border-white/10 p-6 rounded-lg">
                            <div className="flex items-center mb-4">
                                <div className="w-12 h-12 bg-[#e56b4e] rounded-full flex items-center justify-center text-white font-medium mr-4">
                                    RS
                                </div>
                                <div>
                                    <h4 className="font-medium">Ricardo Silva</h4>
                                    <p className="text-sm text-white/70">Inquilino</p>
                                </div>
                            </div>
                            <p className="text-white/80">
                                "Encontrei meu apartamento em apenas 3 dias. O processo foi super tranquilo e a equipe me ajudou em todas as etapas."
                            </p>
                        </div>

                        <div className="bg-[#2c3e50]/50 border border-white/10 p-6 rounded-lg">
                            <div className="flex items-center mb-4">
                                <div className="w-12 h-12 bg-[#e56b4e] rounded-full flex items-center justify-center text-white font-medium mr-4">
                                    MO
                                </div>
                                <div>
                                    <h4 className="font-medium">Maria Oliveira</h4>
                                    <p className="text-sm text-white/70">Proprietária</p>
                                </div>
                            </div>
                            <p className="text-white/80">
                                "Já aluguei 3 imóveis pela KitNet. A plataforma é intuitiva e encontro inquilinos qualificados rapidamente."
                            </p>
                        </div>

                        <div className="bg-[#2c3e50]/50 border border-white/10 p-6 rounded-lg">
                            <div className="flex items-center mb-4">
                                <div className="w-12 h-12 bg-[#e56b4e] rounded-full flex items-center justify-center text-white font-medium mr-4">
                                    JP
                                </div>
                                <div>
                                    <h4 className="font-medium">João Pereira</h4>
                                    <p className="text-sm text-white/70">Inquilino</p>
                                </div>
                            </div>
                            <p className="text-white/80">
                                "Os filtros de busca me ajudaram a encontrar exatamente o que eu precisava. Recomendo a todos que estão procurando um imóvel."
                            </p>
                        </div>
                    </div>
                </div>
            </section>

            {/* CTA */}
            <section className="py-16 bg-[#f9f4e8]">
                <div className="px-4 mx-auto max-w-7xl text-center">
                    <h2 className="text-3xl md:text-4xl font-heading font-bold mb-4">
                        Pronto para encontrar seu próximo lar?
                    </h2>
                    <p className="text-lg text-muted-foreground mb-8 max-w-2xl mx-auto">
                        Junte-se a milhares de pessoas que já encontraram o imóvel ideal através da KitNet.
                    </p>
                    <div className="flex flex-wrap gap-4 justify-center">
                        <Link href="/properties">
                            <Button size="lg" className="bg-[#e56b4e] hover:bg-[#e56b4e]/90">
                                Buscar Imóveis
                            </Button>
                        </Link>
                        <Link href="/add-property">
                            <Button size="lg" variant="outline">
                                Anunciar Imóvel
                            </Button>
                        </Link>
                    </div>
                </div>
            </section>
        </MainLayout >
    );
};

// Sample data
const featuredProperties = [
    {
        id: '1',
        title: 'Apartamento moderno com vista para o mar',
        location: 'Copacabana, Rio de Janeiro',
        price: 2500,
        image: 'https://images.unsplash.com/photo-1483058712412-4245e9b90334?auto=format&fit=crop&w=600&h=400&q=80',
        type: 'Apartamento'
    },
    {
        id: '2',
        title: 'Casa espaçosa com jardim',
        location: 'Morumbi, São Paulo',
        price: 4200,
        image: 'https://images.unsplash.com/photo-1487958449943-2429e8be8625?auto=format&fit=crop&w=600&h=400&q=80',
        type: 'Casa'
    },
    {
        id: '3',
        title: 'Studio mobiliado no centro',
        location: 'Centro, Florianópolis',
        price: 1800,
        image: 'https://images.unsplash.com/photo-1518005020951-eccb494ad742?auto=format&fit=crop&w=600&h=400&q=80',
        type: 'Studio'
    }
];

export default WelcomePage;