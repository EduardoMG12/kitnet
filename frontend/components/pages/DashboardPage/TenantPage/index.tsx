'use client';

import React from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import {
    Search,
    Heart,
    FileText,
    MessageSquare,
    Calendar,
    TrendingUp,
    MapPin,
    Clock,
    Star
} from "lucide-react";
import Link from "next/link";

const DashboardTenantPage = () => {
    return (
        <div className="min-h-screen flex flex-col">

            <main className="flex-1 p-6 max-w-7xl mx-auto w-full">
                {/* Welcome Section */}
                <div className="mb-8">
                    <h1 className="font-heading text-3xl font-bold mb-2">
                        Bem-vindo de volta, João!
                    </h1>
                    <p className="text-muted-foreground">
                        Encontre seu próximo lar com facilidade
                    </p>
                </div>

                {/* Quick Actions */}
                <div className="grid grid-cols-1 md:grid-cols-4 gap-4 mb-8">
                    <Card className="hover:shadow-md transition-shadow cursor-pointer">
                        <CardContent className="p-4 text-center">
                            <Search className="h-8 w-8 mx-auto mb-2 text-terracotta" />
                            <h3 className="font-semibold mb-1">Buscar Imóveis</h3>
                            <p className="text-sm text-muted-foreground">Encontre sua casa ideal</p>
                        </CardContent>
                    </Card>

                    <Card className="hover:shadow-md transition-shadow cursor-pointer">
                        <CardContent className="p-4 text-center">
                            <Heart className="h-8 w-8 mx-auto mb-2 text-terracotta" />
                            <h3 className="font-semibold mb-1">Favoritos</h3>
                            <p className="text-sm text-muted-foreground">Seus imóveis salvos</p>
                        </CardContent>
                    </Card>

                    <Card className="hover:shadow-md transition-shadow cursor-pointer">
                        <CardContent className="p-4 text-center">
                            <FileText className="h-8 w-8 mx-auto mb-2 text-terracotta" />
                            <h3 className="font-semibold mb-1">Propostas</h3>
                            <p className="text-sm text-muted-foreground">Acompanhe suas propostas</p>
                        </CardContent>
                    </Card>

                    <Card className="hover:shadow-md transition-shadow cursor-pointer">
                        <CardContent className="p-4 text-center">
                            <MessageSquare className="h-8 w-8 mx-auto mb-2 text-terracotta" />
                            <h3 className="font-semibold mb-1">Mensagens</h3>
                            <p className="text-sm text-muted-foreground">2 novas mensagens</p>
                        </CardContent>
                    </Card>
                </div>

                {/* Main Content Grid */}
                <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
                    {/* Left Column - Recent Activity */}
                    <div className="lg:col-span-2 space-y-6">
                        {/* Recent Searches */}
                        <Card>
                            <CardHeader>
                                <CardTitle className="flex items-center gap-2">
                                    <Clock className="h-5 w-5" />
                                    Buscas Recentes
                                </CardTitle>
                                <CardDescription>
                                    Continue de onde parou
                                </CardDescription>
                            </CardHeader>
                            <CardContent>
                                <div className="space-y-3">
                                    <div className="flex items-center justify-between p-3 bg-muted/50 rounded-lg">
                                        <div className="flex items-center gap-3">
                                            <MapPin className="h-4 w-4 text-muted-foreground" />
                                            <div>
                                                <p className="font-medium">Apartamentos em Copacabana</p>
                                                <p className="text-sm text-muted-foreground">2-3 quartos • R$ 2.000 - R$ 4.000</p>
                                            </div>
                                        </div>
                                        <Button variant="ghost" size="sm">Ver resultados</Button>
                                    </div>

                                    <div className="flex items-center justify-between p-3 bg-muted/50 rounded-lg">
                                        <div className="flex items-center gap-3">
                                            <MapPin className="h-4 w-4 text-muted-foreground" />
                                            <div>
                                                <p className="font-medium">Casas em Vila Madalena</p>
                                                <p className="text-sm text-muted-foreground">3+ quartos • R$ 3.500 - R$ 6.000</p>
                                            </div>
                                        </div>
                                        <Button variant="ghost" size="sm">Ver resultados</Button>
                                    </div>
                                </div>
                            </CardContent>
                        </Card>

                        {/* Featured Properties */}
                        <Card>
                            <CardHeader>
                                <CardTitle className="flex items-center gap-2">
                                    <Star className="h-5 w-5" />
                                    Imóveis em Destaque
                                </CardTitle>
                                <CardDescription>
                                    Seleções especiais para você
                                </CardDescription>
                            </CardHeader>
                            <CardContent>
                                <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                                    <div className="border rounded-lg overflow-hidden">
                                        <img
                                            src="https://images.unsplash.com/photo-1483058712412-4245e9b90334?auhref=format&fit=crop&w=300&h=200&q=80"
                                            alt="Apartamento em Ipanema"
                                            className="w-full h-32 object-cover"
                                        />
                                        <div className="p-3">
                                            <h4 className="font-semibold mb-1">Apartamento em Ipanema</h4>
                                            <p className="text-sm text-muted-foreground mb-2">2 quartos • 1 banheiro • 75m²</p>
                                            <p className="text-terracotta font-semibold">R$ 3.200/mês</p>
                                        </div>
                                    </div>

                                    <div className="border rounded-lg overflow-hidden">
                                        <img
                                            src="https://images.unsplash.com/photo-1487958449943-2429e8be8625?auhref=format&fit=crop&w=300&h=200&q=80"
                                            alt="Casa em Botafogo"
                                            className="w-full h-32 object-cover"
                                        />
                                        <div className="p-3">
                                            <h4 className="font-semibold mb-1">Casa em Botafogo</h4>
                                            <p className="text-sm text-muted-foreground mb-2">3 quartos • 2 banheiros • 120m²</p>
                                            <p className="text-terracotta font-semibold">R$ 4.500/mês</p>
                                        </div>
                                    </div>
                                </div>
                            </CardContent>
                        </Card>
                    </div>

                    {/* Right Column - Sidebar */}
                    <div className="space-y-6">
                        {/* Active Proposals */}
                        <Card>
                            <CardHeader>
                                <CardTitle className="text-lg">Propostas Ativas</CardTitle>
                            </CardHeader>
                            <CardContent>
                                <div className="space-y-3">
                                    <div className="flex items-center justify-between">
                                        <div>
                                            <p className="font-medium text-sm">Apt. Leblon</p>
                                            <p className="text-xs text-muted-foreground">Enviada há 2 dias</p>
                                        </div>
                                        <Badge variant="secondary">Pendente</Badge>
                                    </div>

                                    <div className="flex items-center justify-between">
                                        <div>
                                            <p className="font-medium text-sm">Casa Tijuca</p>
                                            <p className="text-xs text-muted-foreground">Enviada há 5 dias</p>
                                        </div>
                                        <Badge className="bg-green-100 text-green-800 hover:bg-green-100">Aceita</Badge>
                                    </div>
                                </div>

                                <Button variant="outline" className="w-full mt-4" size="sm">
                                    Ver todas as propostas
                                </Button>
                            </CardContent>
                        </Card>

                        {/* Market Insights */}
                        <Card>
                            <CardHeader>
                                <CardTitle className="text-lg flex items-center gap-2">
                                    <TrendingUp className="h-4 w-4" />
                                    Insights do Mercado
                                </CardTitle>
                            </CardHeader>
                            <CardContent>
                                <div className="space-y-3">
                                    <div className="p-3 bg-blue-50 rounded-lg">
                                        <p className="text-sm font-medium text-blue-900">Preços em Copacabana</p>
                                        <p className="text-xs text-blue-700">↑ 3.2% este mês</p>
                                    </div>

                                    <div className="p-3 bg-green-50 rounded-lg">
                                        <p className="text-sm font-medium text-green-900">Novas oportunidades</p>
                                        <p className="text-xs text-green-700">15 novos imóveis na sua área</p>
                                    </div>
                                </div>

                                <Button variant="outline" className="w-full mt-4" size="sm">
                                    Ver relatório completo
                                </Button>
                            </CardContent>
                        </Card>

                        {/* Scheduled Visits */}
                        <Card>
                            <CardHeader>
                                <CardTitle className="text-lg flex items-center gap-2">
                                    <Calendar className="h-4 w-4" />
                                    Próximas Visitas
                                </CardTitle>
                            </CardHeader>
                            <CardContent>
                                <div className="space-y-3">
                                    <div className="flex items-center gap-3 p-2 border rounded">
                                        <div className="text-center">
                                            <p className="text-xs font-medium">SEX</p>
                                            <p className="text-lg font-bold text-terracotta">15</p>
                                        </div>
                                        <div className="flex-1">
                                            <p className="font-medium text-sm">Apt. em Ipanema</p>
                                            <p className="text-xs text-muted-foreground">14:00 - 15:00</p>
                                        </div>
                                    </div>
                                </div>

                                <Button variant="outline" className="w-full mt-4" size="sm">
                                    Agendar nova visita
                                </Button>
                            </CardContent>
                        </Card>
                    </div>
                </div>

                {/* CTA Section */}
                <div className="mt-12 text-center">
                    <Card className="bg-gradient-to-r from-terracotta/10 to-terracotta/5 border-terracotta/20">
                        <CardContent className="p-8">
                            <h2 className="font-heading text-2xl font-bold mb-4">
                                Pronto para encontrar sua nova casa?
                            </h2>
                            <p className="text-muted-foreground mb-6 max-w-2xl mx-auto">
                                Explore nossa seleção de imóveis cuidadosamente verificados e encontre o lar perfeito para você.
                            </p>
                            <Link href="/properties">
                                <Button size="lg" className="bg-terracotta hover:bg-terracotta/90">
                                    Explorar Imóveis
                                </Button>
                            </Link>
                        </CardContent>
                    </Card>
                </div>
            </main>
        </div>
    );
};

export default DashboardTenantPage;