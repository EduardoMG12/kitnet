"use client";

import React, { useState } from "react";
import MainLayout from "@/components/layout/MainLayout";
import PropertySidebar from "@/components/layout/PropertySidebar";
import PropertyCard from "@/components/PropertyCard";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Grid3X3, LayoutList, Search } from "lucide-react";

import { ArrowRight, Bed, Bath, MapPin } from "lucide-react";
import { Home as HomeIcon } from "lucide-react";
import Link from "next/link";


const AvalibleProperties = () => {
    const [viewMode, setViewMode] = useState<"grid" | "list">("grid");

    return (
        <MainLayout>
            <div className="flex min-h-screen">
                <PropertySidebar className="hidden md:block" />

                <div className="flex-1 p-6">
                    <div className="mb-6">
                        <h1 className="font-heading text-2xl font-bold mb-2">
                            Imóveis disponíveis
                        </h1>
                        <p className="text-muted-foreground">
                            Encontre o imóvel ideal para você entre as nossas {properties.length} opções
                        </p>
                    </div>

                    <div className="flex flex-col sm:flex-row gap-4 items-center justify-between mb-6">
                        <div className="w-full sm:max-w-md relative">
                            <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-muted-foreground h-4 w-4" />
                            <Input
                                placeholder="Buscar por localização ou característica..."
                                className="pl-9"
                            />
                        </div>

                        <div className="flex items-center gap-3 w-full sm:w-auto">
                            <Select defaultValue="recent">
                                <SelectTrigger className="w-[180px]">
                                    <SelectValue placeholder="Ordenar por" />
                                </SelectTrigger>
                                <SelectContent>
                                    <SelectItem value="recent">Mais recentes</SelectItem>
                                    <SelectItem value="price-asc">Menor preço</SelectItem>
                                    <SelectItem value="price-desc">Maior preço</SelectItem>
                                    <SelectItem value="area-desc">Maior área</SelectItem>
                                </SelectContent>
                            </Select>

                            <div className="flex rounded-md border overflow-hidden">
                                <Button
                                    variant={viewMode === "grid" ? "default" : "ghost"}
                                    size="icon"
                                    onClick={() => setViewMode("grid")}
                                    className={viewMode === "grid" ? "bg-terracotta hover:bg-terracotta/90" : ""}
                                >
                                    <Grid3X3 className="h-4 w-4" />
                                </Button>
                                <Button
                                    variant={viewMode === "list" ? "default" : "ghost"}
                                    size="icon"
                                    onClick={() => setViewMode("list")}
                                    className={viewMode === "list" ? "bg-terracotta hover:bg-terracotta/90" : ""}
                                >
                                    <LayoutList className="h-4 w-4" />
                                </Button>
                            </div>
                        </div>
                    </div>

                    <div className="mb-4">
                        <div className="flex gap-2 items-center">
                            <p className="text-sm text-muted-foreground">Filtros ativos:</p>
                            <Button variant="outline" size="sm" className="h-7 text-xs gap-1">
                                São Paulo
                                <span className="ml-1">×</span>
                            </Button>
                            <Button variant="outline" size="sm" className="h-7 text-xs gap-1">
                                Apartamento
                                <span className="ml-1">×</span>
                            </Button>
                            <Button variant="link" size="sm" className="h-7 text-xs text-terracotta">
                                Limpar todos
                            </Button>
                        </div>
                    </div>

                    {viewMode === "grid" ? (
                        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
                            {properties.map((property) => (
                                <PropertyCard
                                    key={property.id}
                                    id={property.id}
                                    title={property.title}
                                    location={property.location}
                                    price={property.price}
                                    image={property.image}
                                    type={property.type}
                                    bedrooms={property.bedrooms}
                                    bathrooms={property.bathrooms}
                                    area={property.area}
                                />
                            ))}
                        </div>
                    ) : (
                        <div className="space-y-4">
                            {properties.map((property) => (
                                <div key={property.id} className="flex flex-col md:flex-row border rounded-lg overflow-hidden bg-card">
                                    <div className="md:w-1/3 h-48 md:h-auto relative">
                                        <img
                                            src={property.image}
                                            alt={property.title}
                                            className="w-full h-full object-cover"
                                        />
                                    </div>
                                    <div className="p-4 md:p-6 flex flex-col flex-1">
                                        <div className="flex flex-col md:flex-row md:justify-between md:items-start mb-4">
                                            <div>
                                                <h3 className="font-heading font-medium text-lg mb-2">{property.title}</h3>
                                                <div className="flex items-center text-muted-foreground">
                                                    <MapPin className="h-4 w-4 mr-1" />
                                                    <span>{property.location}</span>
                                                </div>
                                            </div>
                                            <div className="mt-2 md:mt-0">
                                                <p className="text-terracotta font-heading font-medium text-lg">
                                                    R${property.price.toLocaleString('pt-BR')}
                                                </p>
                                                <span className="text-xs text-muted-foreground">por mês</span>
                                            </div>
                                        </div>

                                        <div className="flex gap-4 mt-auto pt-4 border-t">
                                            <div className="flex items-center">
                                                <Bed className="h-4 w-4 mr-1 text-muted-foreground" />
                                                <span className="text-sm">{property.bedrooms} quartos</span>
                                            </div>
                                            <div className="flex items-center">
                                                <Bath className="h-4 w-4 mr-1 text-muted-foreground" />
                                                <span className="text-sm">{property.bathrooms} banheiros</span>
                                            </div>
                                            <div className="flex items-center">
                                                <HomeIcon className="h-4 w-4 mr-1 text-muted-foreground" />
                                                <span className="text-sm">{property.area} m²</span>
                                            </div>

                                            <div className="ml-auto">
                                                <Link
                                                    href={`/properties/${property.id}`}
                                                    className="text-terracotta hover:underline text-sm font-medium flex items-center"
                                                >
                                                    Ver detalhes
                                                    <ArrowRight className="ml-1 h-3.5 w-3.5" />
                                                </Link>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            ))}
                        </div>
                    )}

                    <div className="mt-8 flex justify-center">
                        <div className="join">
                            <Button variant="outline" size="sm" className="join-item">
                                Anterior
                            </Button>
                            <Button variant="outline" size="sm" className="join-item bg-terracotta text-white hover:bg-terracotta/90">
                                1
                            </Button>
                            <Button variant="outline" size="sm" className="join-item">
                                2
                            </Button>
                            <Button variant="outline" size="sm" className="join-item">
                                3
                            </Button>
                            <Button variant="outline" size="sm" className="join-item">
                                Próximo
                            </Button>
                        </div>
                    </div>
                </div>
            </div>
        </MainLayout>
    );
};

// Sample data
const properties = [
    {
        id: '1',
        title: 'Apartamento moderno com vista para o mar',
        location: 'Copacabana, Rio de Janeiro',
        price: 2500,
        image: 'https://images.unsplash.com/photo-1483058712412-4245e9b90334?auto=format&fit=crop&w=600&h=400&q=80',
        type: 'Apartamento',
        bedrooms: 2,
        bathrooms: 1,
        area: 75
    },
    {
        id: '2',
        title: 'Casa espaçosa com jardim',
        location: 'Morumbi, São Paulo',
        price: 4200,
        image: 'https://images.unsplash.com/photo-1487958449943-2429e8be8625?auto=format&fit=crop&w=600&h=400&q=80',
        type: 'Casa',
        bedrooms: 4,
        bathrooms: 3,
        area: 180
    },
    {
        id: '3',
        title: 'Studio mobiliado no centro',
        location: 'Centro, Florianópolis',
        price: 1800,
        image: 'https://images.unsplash.com/photo-1518005020951-eccb494ad742?auto=format&fit=crop&w=600&h=400&q=80',
        type: 'Studio',
        bedrooms: 1,
        bathrooms: 1,
        area: 45
    },
    {
        id: '4',
        title: 'Apartamento com terraço e churrasqueira',
        location: 'Botafogo, Rio de Janeiro',
        price: 3200,
        image: 'https://images.unsplash.com/photo-1460925895917-afdab827c52f?auto=format&fit=crop&w=600&h=400&q=80',
        type: 'Apartamento',
        bedrooms: 3,
        bathrooms: 2,
        area: 110
    },
    {
        id: '5',
        title: 'Casa de condomínio com piscina',
        location: 'Alphaville, São Paulo',
        price: 6500,
        image: 'https://images.unsplash.com/photo-1483058712412-4245e9b90334?auto=format&fit=crop&w=600&h=400&q=80',
        type: 'Casa',
        bedrooms: 4,
        bathrooms: 4,
        area: 250
    },
    {
        id: '6',
        title: 'Loft industrial reformado',
        location: 'Vila Madalena, São Paulo',
        price: 2900,
        image: 'https://images.unsplash.com/photo-1487958449943-2429e8be8625?auto=format&fit=crop&w=600&h=400&q=80',
        type: 'Loft',
        bedrooms: 1,
        bathrooms: 1,
        area: 70
    }
];

export default AvalibleProperties;