import React from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import {
    Plus,
    Home,
    FileText,
    MessageSquare,
    TrendingUp,
    DollarSign,
    Users,
    Calendar,
    AlertCircle,
    CheckCircle,
    Clock
} from "lucide-react";
import Link from "next/link";

const DashboardLandlordPage = () => {
    return (
        <div className="min-h-screen flex flex-col">
            <main className="flex-1 p-6 max-w-7xl mx-auto w-full">
                {/* Welcome Section */}
                <div className="mb-8">
                    <h1 className="font-heading text-3xl font-bold mb-2">
                        Bem-vindo, Maria Silva!
                    </h1>
                    <p className="text-muted-foreground">
                        Gerencie seus imóveis e maximize sua rentabilidade
                    </p>
                </div>

                {/* KPI Cards */}
                <div className="grid grid-cols-1 md:grid-cols-4 gap-4 mb-8">
                    <Card>
                        <CardContent className="p-4">
                            <div className="flex items-center justify-between">
                                <div>
                                    <p className="text-sm font-medium text-muted-foreground">Imóveis Ativos</p>
                                    <p className="text-2xl font-bold">12</p>
                                </div>
                                <Home className="h-8 w-8 text-[#e56b4e]" />
                            </div>
                            <p className="text-xs text-muted-foreground mt-2">
                                <span className="text-green-600">+2</span> este mês
                            </p>
                        </CardContent>
                    </Card>

                    <Card>
                        <CardContent className="p-4">
                            <div className="flex items-center justify-between">
                                <div>
                                    <p className="text-sm font-medium text-muted-foreground">Receita Mensal</p>
                                    <p className="text-2xl font-bold">R$ 28.400</p>
                                </div>
                                <DollarSign className="h-8 w-8 text-green-600" />
                            </div>
                            <p className="text-xs text-muted-foreground mt-2">
                                <span className="text-green-600">+12%</span> vs mês anterior
                            </p>
                        </CardContent>
                    </Card>

                    <Card>
                        <CardContent className="p-4">
                            <div className="flex items-center justify-between">
                                <div>
                                    <p className="text-sm font-medium text-muted-foreground">Taxa de Ocupação</p>
                                    <p className="text-2xl font-bold">91%</p>
                                </div>
                                <Users className="h-8 w-8 text-blue-600" />
                            </div>
                            <p className="text-xs text-muted-foreground mt-2">
                                11/12 imóveis ocupados
                            </p>
                        </CardContent>
                    </Card>

                    <Card>
                        <CardContent className="p-4">
                            <div className="flex items-center justify-between">
                                <div>
                                    <p className="text-sm font-medium text-muted-foreground">Novas Propostas</p>
                                    <p className="text-2xl font-bold">7</p>
                                </div>
                                <FileText className="h-8 w-8 text-orange-600" />
                            </div>
                            <p className="text-xs text-muted-foreground mt-2">
                                Aguardando análise
                            </p>
                        </CardContent>
                    </Card>
                </div>

                {/* Quick Actions */}
                <div className="grid grid-cols-1 md:grid-cols-4 gap-4 mb-8">
                    <Link href="/add-property">
                        <Card className="hover:shadow-md transition-shadow cursor-pointer border-dashed border-2 border-[#e56b4e]/30">
                            <CardContent className="p-4 text-center">
                                <Plus className="h-8 w-8 mx-auto mb-2 text-[#e56b4e]" />
                                <h3 className="font-semibold mb-1">Anunciar Imóvel</h3>
                                <p className="text-sm text-muted-foreground">Adicione um novo imóvel</p>
                            </CardContent>
                        </Card>
                    </Link>

                    <Card className="hover:shadow-md transition-shadow cursor-pointer">
                        <CardContent className="p-4 text-center">
                            <FileText className="h-8 w-8 mx-auto mb-2 text-[#e56b4e]" />
                            <h3 className="font-semibold mb-1">Propostas</h3>
                            <p className="text-sm text-muted-foreground">Gerencie solicitações</p>
                        </CardContent>
                    </Card>

                    <Card className="hover:shadow-md transition-shadow cursor-pointer">
                        <CardContent className="p-4 text-center">
                            <MessageSquare className="h-8 w-8 mx-auto mb-2 text-[#e56b4e]" />
                            <h3 className="font-semibold mb-1">Mensagens</h3>
                            <p className="text-sm text-muted-foreground">5 não lidas</p>
                        </CardContent>
                    </Card>

                    <Card className="hover:shadow-md transition-shadow cursor-pointer">
                        <CardContent className="p-4 text-center">
                            <TrendingUp className="h-8 w-8 mx-auto mb-2 text-[#e56b4e]" />
                            <h3 className="font-semibold mb-1">Relatórios</h3>
                            <p className="text-sm text-muted-foreground">Análise de desempenho</p>
                        </CardContent>
                    </Card>
                </div>

                {/* Main Content Grid */}
                <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
                    {/* Left Column */}
                    <div className="lg:col-span-2 space-y-6">
                        {/* Recent Activity */}
                        <Card>
                            <CardHeader>
                                <CardTitle className="flex items-center gap-2">
                                    <Clock className="h-5 w-5" />
                                    Atividades Recentes
                                </CardTitle>
                                <CardDescription>
                                    Últimas atualizações dos seus imóveis
                                </CardDescription>
                            </CardHeader>
                            <CardContent>
                                <div className="space-y-4">
                                    <div className="flex items-start gap-3">
                                        <CheckCircle className="h-5 w-5 text-green-600 mt-0.5" />
                                        <div className="flex-1">
                                            <p className="font-medium">Nova proposta aceita</p>
                                            <p className="text-sm text-muted-foreground">Apartamento em Copacabana - R$ 3.200/mês</p>
                                            <p className="text-xs text-muted-foreground">Há 2 horas</p>
                                        </div>
                                    </div>

                                    <div className="flex items-start gap-3">
                                        <AlertCircle className="h-5 w-5 text-orange-600 mt-0.5" />
                                        <div className="flex-1">
                                            <p className="font-medium">Manutenção solicitada</p>
                                            <p className="text-sm text-muted-foreground">Casa em Vila Madalena - Problema na pia da cozinha</p>
                                            <p className="text-xs text-muted-foreground">Há 1 dia</p>
                                        </div>
                                    </div>

                                    <div className="flex items-start gap-3">
                                        <DollarSign className="h-5 w-5 text-green-600 mt-0.5" />
                                        <div className="flex-1">
                                            <p className="font-medium">Pagamento recebido</p>
                                            <p className="text-sm text-muted-foreground">Studio no Centro - R$ 1.800 (Janeiro/2024)</p>
                                            <p className="text-xs text-muted-foreground">Há 2 dias</p>
                                        </div>
                                    </div>
                                </div>
                            </CardContent>
                        </Card>

                        {/* Properties Overview */}
                        <Card>
                            <CardHeader>
                                <CardTitle>Seus Imóveis</CardTitle>
                                <CardDescription>
                                    Visão geral dos seus principais imóveis
                                </CardDescription>
                            </CardHeader>
                            <CardContent>
                                <div className="space-y-4">
                                    <div className="flex items-center justify-between p-4 border rounded-lg">
                                        <div className="flex items-center gap-4">
                                            <img
                                                src="https://images.unsplash.com/photo-1483058712412-4245e9b90334?auhref=format&fit=crop&w=80&h=60&q=80"
                                                alt="Apartamento Copacabana"
                                                className="w-16 h-12 object-cover rounded"
                                            />
                                            <div>
                                                <h4 className="font-semibold">Apartamento - Copacabana</h4>
                                                <p className="text-sm text-muted-foreground">2 quartos • 75m² • R$ 3.200/mês</p>
                                            </div>
                                        </div>
                                        <div className="text-right">
                                            <Badge className="bg-green-100 text-green-800 hover:bg-green-100">Ocupado</Badge>
                                            <p className="text-xs text-muted-foreground mt-1">Vence em 15 dias</p>
                                        </div>
                                    </div>

                                    <div className="flex items-center justify-between p-4 border rounded-lg">
                                        <div className="flex items-center gap-4">
                                            <img
                                                src="https://images.unsplash.com/photo-1487958449943-2429e8be8625?auhref=format&fit=crop&w=80&h=60&q=80"
                                                alt="Casa Vila Madalena"
                                                className="w-16 h-12 object-cover rounded"
                                            />
                                            <div>
                                                <h4 className="font-semibold">Casa - Vila Madalena</h4>
                                                <p className="text-sm text-muted-foreground">3 quartos • 120m² • R$ 4.500/mês</p>
                                            </div>
                                        </div>
                                        <div className="text-right">
                                            <Badge variant="secondary">Disponível</Badge>
                                            <p className="text-xs text-muted-foreground mt-1">3 interessados</p>
                                        </div>
                                    </div>
                                </div>

                                <Button variant="outline" className="w-full mt-4">
                                    Ver todos os imóveis
                                </Button>
                            </CardContent>
                        </Card>
                    </div>

                    {/* Right Column - Sidebar */}
                    <div className="space-y-6">
                        {/* Pending Actions */}
                        <Card>
                            <CardHeader>
                                <CardTitle className="text-lg">Ações Pendentes</CardTitle>
                            </CardHeader>
                            <CardContent>
                                <div className="space-y-3">
                                    <div className="flex items-center justify-between p-2 bg-orange-50 rounded border-l-4 border-orange-400">
                                        <div>
                                            <p className="font-medium text-sm">7 propostas</p>
                                            <p className="text-xs text-muted-foreground">Aguardando análise</p>
                                        </div>
                                        <Button size="sm" variant="outline">Ver</Button>
                                    </div>

                                    <div className="flex items-center justify-between p-2 bg-red-50 rounded border-l-4 border-red-400">
                                        <div>
                                            <p className="font-medium text-sm">1 manutenção</p>
                                            <p className="text-xs text-muted-foreground">Urgente</p>
                                        </div>
                                        <Button size="sm" variant="outline">Ver</Button>
                                    </div>

                                    <div className="flex items-center justify-between p-2 bg-blue-50 rounded border-l-4 border-blue-400">
                                        <div>
                                            <p className="font-medium text-sm">3 contratos</p>
                                            <p className="text-xs text-muted-foreground">Vencendo em 30 dias</p>
                                        </div>
                                        <Button size="sm" variant="outline">Ver</Button>
                                    </div>
                                </div>
                            </CardContent>
                        </Card>

                        {/* Performance */}
                        <Card>
                            <CardHeader>
                                <CardTitle className="text-lg">Desempenho do Mês</CardTitle>
                            </CardHeader>
                            <CardContent>
                                <div className="space-y-4">
                                    <div>
                                        <div className="flex justify-between text-sm mb-1">
                                            <span>Taxa de Ocupação</span>
                                            <span>91%</span>
                                        </div>
                                        <div className="w-full bg-gray-200 rounded-full h-2">
                                            <div className="bg-green-600 h-2 rounded-full" style={{ width: '91%' }} />
                                        </div>
                                    </div>

                                    <div>
                                        <div className="flex justify-between text-sm mb-1">
                                            <span>Receita vs Meta</span>
                                            <span>112%</span>
                                        </div>
                                        <div className="w-full bg-gray-200 rounded-full h-2">
                                            <div className="bg-[#e56b4e] h-2 rounded-full" style={{ width: '100%' }} />
                                        </div>
                                    </div>
                                </div>
                            </CardContent>
                        </Card>

                        {/* Calendar */}
                        <Card>
                            <CardHeader>
                                <CardTitle className="text-lg flex items-center gap-2">
                                    <Calendar className="h-4 w-4" />
                                    Próximos Eventos
                                </CardTitle>
                            </CardHeader>
                            <CardContent>
                                <div className="space-y-3">
                                    <div className="flex items-center gap-3 p-2 border rounded">
                                        <div className="text-center">
                                            <p className="text-xs font-medium">HOJ</p>
                                            <p className="text-lg font-bold text-[#e56b4e]">20</p>
                                        </div>
                                        <div className="flex-1">
                                            <p className="font-medium text-sm">Visita agendada</p>
                                            <p className="text-xs text-muted-foreground">15:00 - Casa Vila Madalena</p>
                                        </div>
                                    </div>

                                    <div className="flex items-center gap-3 p-2 border rounded">
                                        <div className="text-center">
                                            <p className="text-xs font-medium">SEG</p>
                                            <p className="text-lg font-bold text-gray-600">21</p>
                                        </div>
                                        <div className="flex-1">
                                            <p className="font-medium text-sm">Vistoria</p>
                                            <p className="text-xs text-muted-foreground">10:00 - Apt. Ipanema</p>
                                        </div>
                                    </div>
                                </div>
                            </CardContent>
                        </Card>
                    </div>
                </div>
            </main>
        </div>
    );
};

export default DashboardLandlordPage;