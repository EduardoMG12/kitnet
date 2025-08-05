'use client';

import React from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { ArrowLeft, Download, CheckCircle } from "lucide-react";
import MainLayout from "@/components/layout/MainLayout";
import { Breadcrumb, BreadcrumbItem, BreadcrumbLink, BreadcrumbList, BreadcrumbPage, BreadcrumbSeparator } from "@/components/ui/breadcrumb";
import Link from "next/link";

const RentalContractPage = () => {
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
                            <BreadcrumbPage>Contrato de Aluguel</BreadcrumbPage>
                        </BreadcrumbList>
                    </Breadcrumb>

                    <div className="max-w-4xl mx-auto">
                        <header className="mb-8">
                            <h1 className="text-3xl md:text-4xl font-heading font-bold mb-4">
                                O que deve constar em um contrato de aluguel
                            </h1>
                            <p className="text-lg text-muted-foreground">
                                Guia completo sobre os elementos essenciais de um contrato de aluguel + modelo para download
                            </p>
                        </header>

                        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
                            <div className="lg:col-span-2 space-y-8">
                                <Card>
                                    <CardHeader>
                                        <CardTitle className="flex items-center gap-2">
                                            <CheckCircle className="h-5 w-5 text-green-600" />
                                            Informações Básicas Obrigatórias
                                        </CardTitle>
                                    </CardHeader>
                                    <CardContent className="space-y-4">
                                        <div>
                                            <h3 className="font-medium mb-2">1. Dados das Partes</h3>
                                            <ul className="text-sm text-muted-foreground space-y-1 ml-4">
                                                <li>• Nome completo, CPF/CNPJ, RG, estado civil</li>
                                                <li>• Endereço completo de locador e locatário</li>
                                                <li>• Dados dos fiadores (se houver)</li>
                                            </ul>
                                        </div>

                                        <div>
                                            <h3 className="font-medium mb-2">2. Descrição do Imóvel</h3>
                                            <ul className="text-sm text-muted-foreground space-y-1 ml-4">
                                                <li>• Endereço completo do imóvel</li>
                                                <li>• Área, número de cômodos, características</li>
                                                <li>• Estado de conservação</li>
                                                <li>• Mobília inclusa (se aplicável)</li>
                                            </ul>
                                        </div>

                                        <div>
                                            <h3 className="font-medium mb-2">3. Condições Financeiras</h3>
                                            <ul className="text-sm text-muted-foreground space-y-1 ml-4">
                                                <li>• Valor do aluguel mensal</li>
                                                <li>• Forma e data de pagamento</li>
                                                <li>• Reajuste anual (índice utilizado)</li>
                                                <li>• Valor do depósito caução</li>
                                                <li>• Taxas de condomínio e IPTU</li>
                                            </ul>
                                        </div>
                                    </CardContent>
                                </Card>

                                <Card>
                                    <CardHeader>
                                        <CardTitle>Cláusulas Importantes</CardTitle>
                                    </CardHeader>
                                    <CardContent className="space-y-4">
                                        <div>
                                            <h3 className="font-medium mb-2">Prazo e Renovação</h3>
                                            <p className="text-sm text-muted-foreground">
                                                Definir se o contrato é por prazo determinado (até 30 meses) ou indeterminado.
                                                Incluir condições para renovação automática ou renegociação.
                                            </p>
                                        </div>

                                        <div>
                                            <h3 className="font-medium mb-2">Rescisão Antecipada</h3>
                                            <p className="text-sm text-muted-foreground">
                                                Condições e multas para rescisão antes do prazo. Geralmente equivale a
                                                3 meses de aluguel ou proporcional ao tempo restante.
                                            </p>
                                        </div>

                                        <div>
                                            <h3 className="font-medium mb-2">Benfeitorias</h3>
                                            <p className="text-sm text-muted-foreground">
                                                Definir quais melhorias podem ser feitas pelo inquilino e se serão
                                                indenizadas ao final do contrato.
                                            </p>
                                        </div>
                                    </CardContent>
                                </Card>

                                <Card>
                                    <CardHeader>
                                        <CardTitle>Dicas Importantes</CardTitle>
                                    </CardHeader>
                                    <CardContent className="space-y-4">
                                        <div className="bg-amber-50 p-4 rounded-lg border-l-4 border-amber-400">
                                            <h4 className="font-medium text-amber-800 mb-2">⚠️ Atenção</h4>
                                            <p className="text-sm text-amber-700">
                                                Sempre leia todo o contrato antes de assinar. Se tiver dúvidas,
                                                consulte um advogado especializado em direito imobiliário.
                                            </p>
                                        </div>

                                        <div className="bg-green-50 p-4 rounded-lg border-l-4 border-green-400">
                                            <h4 className="font-medium text-green-800 mb-2">✅ Recomendação</h4>
                                            <p className="text-sm text-green-700">
                                                Faça uma vistoria detalhada antes da assinatura e documente
                                                o estado do imóvel com fotos.
                                            </p>
                                        </div>
                                    </CardContent>
                                </Card>
                            </div>

                            <div className="space-y-6">
                                <Card>
                                    <CardHeader>
                                        <CardTitle className="text-lg">Modelo de Contrato</CardTitle>
                                    </CardHeader>
                                    <CardContent>
                                        <p className="text-sm text-muted-foreground mb-4">
                                            Baixe nosso modelo de contrato de aluguel completo e personalizado
                                        </p>
                                        <Button className="w-full bg-[#e56b4e] hover:bg-[#e56b4e]/90">
                                            <Download className="mr-2 h-4 w-4" />
                                            Baixar Modelo
                                        </Button>
                                    </CardContent>
                                </Card>

                                <Card>
                                    <CardHeader>
                                        <CardTitle className="text-lg">Artigos Relacionados</CardTitle>
                                    </CardHeader>
                                    <CardContent className="space-y-3">
                                        <Link href="/guides/lei-inquilinato" className="block text-sm text-terracotta hover:underline">
                                            → Lei do Inquilinato
                                        </Link>
                                        <Link href="/guides/quebra-contrato" className="block text-sm text-terracotta hover:underline">
                                            → Quebra de Contrato
                                        </Link>
                                        <Link href="/guides/vistoria" className="block text-sm text-terracotta hover:underline">
                                            → Como fazer vistoria
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

export default RentalContractPage;