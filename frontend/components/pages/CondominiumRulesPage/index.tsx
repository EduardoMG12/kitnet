import React from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { ArrowLeft, FileCheck, Users } from "lucide-react";
import MainLayout from "@/components/layout/MainLayout";
import { Breadcrumb, BreadcrumbItem, BreadcrumbLink, BreadcrumbList, BreadcrumbPage, BreadcrumbSeparator } from "@/components/ui/breadcrumb";
import Link from "next/link";

const CondominiumRules = () => {
    return (
        <MainLayout>
            <div className="py-8 bg-white">
                <div className="px-4 mx-auto max-w-7xl">
                    <Breadcrumb className="mb-6">
                        <BreadcrumbList>
                            <BreadcrumbItem>
                                <BreadcrumbLink asChild>
                                    <Link href="/guides">Guias</Link>
                                </BreadcrumbLink>
                            </BreadcrumbItem>
                            <BreadcrumbSeparator />
                            <BreadcrumbPage>Regras de Condomínio</BreadcrumbPage>
                        </BreadcrumbList>
                    </Breadcrumb>

                    <div className="max-w-4xl mx-auto">
                        <header className="mb-8">
                            <h1 className="text-3xl md:text-4xl font-heading font-bold mb-4">
                                Regras de Condomínio
                            </h1>
                            <p className="text-lg text-muted-foreground">
                                Conheça seus direitos e deveres como condômino
                            </p>
                        </header>

                        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
                            <div className="lg:col-span-2 space-y-8">
                                <Card>
                                    <CardHeader>
                                        <CardTitle className="flex items-center gap-2">
                                            <FileCheck className="h-5 w-5 text-blue-600" />
                                            Convenção de Condomínio
                                        </CardTitle>
                                    </CardHeader>
                                    <CardContent className="space-y-4">
                                        <p className="text-muted-foreground">
                                            A convenção é a lei máxima do condomínio, definindo direitos e deveres de todos.
                                        </p>

                                        <div>
                                            <h3 className="font-medium mb-2">O que define:</h3>
                                            <ul className="text-sm text-muted-foreground space-y-1 ml-4">
                                                <li>• Destinação das unidades autônomas</li>
                                                <li>• Uso das áreas comuns</li>
                                                <li>• Rateio das despesas</li>
                                                <li>• Penalidades por infrações</li>
                                                <li>• Horários de funcionamento</li>
                                            </ul>
                                        </div>
                                    </CardContent>
                                </Card>

                                <Card>
                                    <CardHeader>
                                        <CardTitle>Regimento Interno</CardTitle>
                                    </CardHeader>
                                    <CardContent className="space-y-4">
                                        <div>
                                            <h3 className="font-medium mb-2">Principais Regras</h3>
                                            <ul className="text-sm text-muted-foreground space-y-1 ml-4">
                                                <li>• Horário de silêncio (22h às 7h)</li>
                                                <li>• Uso de elevadores e áreas comuns</li>
                                                <li>• Regras para animais domésticos</li>
                                                <li>• Mudanças e reformas</li>
                                                <li>• Visitantes e prestadores de serviço</li>
                                            </ul>
                                        </div>

                                        <div>
                                            <h3 className="font-medium mb-2">Áreas Comuns</h3>
                                            <ul className="text-sm text-muted-foreground space-y-1 ml-4">
                                                <li>• Piscina, academia, salão de festas</li>
                                                <li>• Horários específicos de funcionamento</li>
                                                <li>• Regras de reserva e uso</li>
                                                <li>• Responsabilidade por danos</li>
                                            </ul>
                                        </div>
                                    </CardContent>
                                </Card>

                                <Card>
                                    <CardHeader>
                                        <CardTitle>Penalidades</CardTitle>
                                    </CardHeader>
                                    <CardContent className="space-y-4">
                                        <div className="bg-red-50 p-4 rounded-lg border-l-4 border-red-400">
                                            <h4 className="font-medium text-red-800 mb-2">⚠️ Infrações Comuns</h4>
                                            <ul className="text-sm text-red-700 space-y-1">
                                                <li>• Perturbação do sossego</li>
                                                <li>• Uso inadequado de áreas comuns</li>
                                                <li>• Atraso no pagamento de taxas</li>
                                                <li>• Danos ao patrimônio comum</li>
                                            </ul>
                                        </div>

                                        <div>
                                            <h3 className="font-medium mb-2">Tipos de Multa</h3>
                                            <ul className="text-sm text-muted-foreground space-y-1 ml-4">
                                                <li>• Multa simples: até 5x a taxa condominial</li>
                                                <li>• Multa por atraso: 2% + juros</li>
                                                <li>• Multa por danos: valor do reparo</li>
                                            </ul>
                                        </div>
                                    </CardContent>
                                </Card>
                            </div>

                            <div className="space-y-6">
                                <Card>
                                    <CardHeader>
                                        <CardTitle className="text-lg">Seus Direitos</CardTitle>
                                    </CardHeader>
                                    <CardContent className="space-y-3">
                                        <div className="text-sm">
                                            <h4 className="font-medium mb-1">Como Condômino</h4>
                                            <ul className="text-muted-foreground space-y-1">
                                                <li>• Usar áreas comuns</li>
                                                <li>• Participar de assembleias</li>
                                                <li>• Votar e ser votado</li>
                                                <li>• Contestar decisões</li>
                                            </ul>
                                        </div>
                                    </CardContent>
                                </Card>

                                <Card>
                                    <CardHeader>
                                        <CardTitle className="text-lg">Artigos Relacionados</CardTitle>
                                    </CardHeader>
                                    <CardContent className="space-y-3">
                                        <Link href="/guides/taxas-condominio" className="block text-sm text-[#e56b4e] hover:underline">
                                            → Taxas de Condomínio
                                        </Link>
                                        <Link href="/guides/assembleias" className="block text-sm text-[#e56b4e] hover:underline">
                                            → Assembleias Condominiais
                                        </Link>
                                        <Link href="/guides/lei-inquilinato" className="block text-sm text-[#e56b4e] hover:underline">
                                            → Lei do Inquilinato
                                        </Link>
                                    </CardContent>
                                </Card>
                            </div>
                        </div>

                        <div className="mt-8">
                            <Link href="/guides">
                                <Button variant="outline">
                                    <ArrowLeft className="mr-2 h-4 w-4" />
                                    Voltar aos Guias
                                </Button>
                            </Link>
                        </div>
                    </div>
                </div>
            </div>
        </MainLayout>
    );
};

export default CondominiumRules;