import React from "react";

import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { ArrowLeft, DollarSign, Calculator } from "lucide-react";
import MainLayout from "@/components/layout/MainLayout";
import { Breadcrumb, BreadcrumbItem, BreadcrumbLink, BreadcrumbList, BreadcrumbPage, BreadcrumbSeparator } from "@/components/ui/breadcrumb";
import Link from "next/link";

const CondominiumFeesPage = () => {
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
                            <BreadcrumbPage>Taxas de Condomínio</BreadcrumbPage>
                        </BreadcrumbList>
                    </Breadcrumb>

                    <div className="max-w-4xl mx-auto">
                        <header className="mb-8">
                            <h1 className="text-3xl md:text-4xl font-heading font-bold mb-4">
                                Taxas de Condomínio
                            </h1>
                            <p className="text-lg text-muted-foreground">
                                Entenda como funcionam as taxas condominiais e quem deve pagá-las
                            </p>
                        </header>

                        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
                            <div className="lg:col-span-2 space-y-8">
                                <Card>
                                    <CardHeader>
                                        <CardTitle className="flex items-center gap-2">
                                            <DollarSign className="h-5 w-5 text-green-600" />
                                            O que são as Taxas
                                        </CardTitle>
                                    </CardHeader>
                                    <CardContent className="space-y-4">
                                        <div>
                                            <h3 className="font-medium mb-2">Taxa Ordinária</h3>
                                            <ul className="text-sm text-muted-foreground space-y-1 ml-4">
                                                <li>• Despesas regulares do condomínio</li>
                                                <li>• Limpeza, segurança, manutenção</li>
                                                <li>• Administração predial</li>
                                                <li>• Seguro do prédio</li>
                                            </ul>
                                        </div>

                                        <div>
                                            <h3 className="font-medium mb-2">Taxa Extraordinária</h3>
                                            <ul className="text-sm text-muted-foreground space-y-1 ml-4">
                                                <li>• Reformas e melhorias</li>
                                                <li>• Reparos emergenciais</li>
                                                <li>• Obras não previstas no orçamento</li>
                                                <li>• Equipamentos novos</li>
                                            </ul>
                                        </div>

                                        <div>
                                            <h3 className="font-medium mb-2">Fundo de Reserva</h3>
                                            <ul className="text-sm text-muted-foreground space-y-1 ml-4">
                                                <li>• Mínimo de 10% das despesas ordinárias</li>
                                                <li>• Para emergências e reformas futuras</li>
                                                <li>• Obrigatório por lei</li>
                                            </ul>
                                        </div>
                                    </CardContent>
                                </Card>

                                <Card>
                                    <CardHeader>
                                        <CardTitle>Quem Paga as Taxas?</CardTitle>
                                    </CardHeader>
                                    <CardContent className="space-y-4">
                                        <div className="bg-blue-50 p-4 rounded-lg border-l-4 border-blue-400">
                                            <h4 className="font-medium text-blue-800 mb-2">⚖️ Por Lei</h4>
                                            <p className="text-sm text-blue-700">
                                                O proprietário é sempre responsável pelas taxas condominiais,
                                                mesmo que o imóvel esteja alugado.
                                            </p>
                                        </div>

                                        <div className="bg-amber-50 p-4 rounded-lg border-l-4 border-amber-400">
                                            <h4 className="font-medium text-amber-800 mb-2">📝 No Contrato</h4>
                                            <p className="text-sm text-amber-700">
                                                É comum transferir a responsabilidade para o inquilino
                                                através de cláusula contratual específica.
                                            </p>
                                        </div>
                                    </CardContent>
                                </Card>

                                <Card>
                                    <CardHeader>
                                        <CardTitle>Como Calcular</CardTitle>
                                    </CardHeader>
                                    <CardContent className="space-y-4">
                                        <div>
                                            <h3 className="font-medium mb-2">Rateio por Fração Ideal</h3>
                                            <p className="text-sm text-muted-foreground">
                                                A fração ideal determina quanto cada unidade paga.
                                                Apartamentos maiores têm frações maiores e pagam mais.
                                            </p>
                                        </div>

                                        <div>
                                            <h3 className="font-medium mb-2">Exemplo de Cálculo</h3>
                                            <p className="text-sm text-muted-foreground">
                                                Se o condomínio tem R$ 10.000 de despesas e sua fração é 2%,
                                                você paga R$ 200.
                                            </p>
                                        </div>
                                    </CardContent>
                                </Card>
                            </div>

                            <div className="space-y-6">
                                <Card>
                                    <CardHeader>
                                        <CardTitle className="text-lg">Calculadora</CardTitle>
                                    </CardHeader>
                                    <CardContent>
                                        <p className="text-sm text-muted-foreground mb-4">
                                            Calcule sua taxa condominial baseada na fração ideal
                                        </p>
                                        <Button className="w-full bg-[#e56b4e] hover:bg-[#e56b4e]/90">
                                            <Calculator className="mr-2 h-4 w-4" />
                                            Calcular Taxa
                                        </Button>
                                    </CardContent>
                                </Card>

                                <Card>
                                    <CardHeader>
                                        <CardTitle className="text-lg">Artigos Relacionados</CardTitle>
                                    </CardHeader>
                                    <CardContent className="space-y-3">
                                        <Link href="/guides/regras-condominio" className="block text-sm text-[#e56b4e] hover:underline">
                                            → Regras de Condomínio
                                        </Link>
                                        <Link href="/guides/assembleias" className="block text-sm text-[#e56b4e] hover:underline">
                                            → Assembleias Condominiais
                                        </Link>
                                        <Link href="/guides/contrato-aluguel" className="block text-sm text-[#e56b4e] hover:underline">
                                            → Contrato de Aluguel
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

export default CondominiumFeesPage;