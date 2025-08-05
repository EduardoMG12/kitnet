'use client';

import React from "react";

import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { ArrowLeft, Download, FileText } from "lucide-react";
import MainLayout from "@/components/layout/MainLayout";
import { Breadcrumb, BreadcrumbItem, BreadcrumbLink, BreadcrumbList, BreadcrumbPage, BreadcrumbSeparator } from "@/components/ui/breadcrumb";
import Link from "next/link";


const ContractModelPage = () => {
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
                            <BreadcrumbPage>Modelo de Contrato</BreadcrumbPage>
                        </BreadcrumbList>
                    </Breadcrumb>

                    <div className="max-w-4xl mx-auto">
                        <header className="mb-8">
                            <h1 className="text-3xl md:text-4xl font-heading font-bold mb-4">
                                Modelo de Contrato de Aluguel
                            </h1>
                            <p className="text-lg text-muted-foreground">
                                Baixe nosso modelo de contrato completo e personalizável para seus imóveis
                            </p>
                        </header>

                        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
                            <div className="lg:col-span-2 space-y-8">
                                <Card>
                                    <CardHeader>
                                        <CardTitle className="flex items-center gap-2">
                                            <FileText className="h-5 w-5 text-[#e56b4e]" />
                                            Sobre o Modelo
                                        </CardTitle>
                                    </CardHeader>
                                    <CardContent className="space-y-4">
                                        <p className="text-muted-foreground">
                                            Nosso modelo de contrato foi desenvolvido seguindo as diretrizes da Lei do Inquilinato
                                            e as melhores práticas do mercado imobiliário. Ele inclui todas as cláusulas essenciais
                                            para uma locação segura e transparente.
                                        </p>

                                        <div>
                                            <h3 className="font-medium mb-2">O que inclui:</h3>
                                            <ul className="text-sm text-muted-foreground space-y-1 ml-4">
                                                <li>• Campos para dados de locador e locatário</li>
                                                <li>• Descrição detalhada do imóvel</li>
                                                <li>• Condições de pagamento e reajuste</li>
                                                <li>• Cláusulas de rescisão</li>
                                                <li>• Termos de benfeitorias</li>
                                                <li>• Responsabilidades das partes</li>
                                            </ul>
                                        </div>
                                    </CardContent>
                                </Card>

                                <Card>
                                    <CardHeader>
                                        <CardTitle>Como Personalizar</CardTitle>
                                    </CardHeader>
                                    <CardContent className="space-y-4">
                                        <div className="bg-blue-50 p-4 rounded-lg border-l-4 border-blue-400">
                                            <h4 className="font-medium text-blue-800 mb-2">💡 Dica</h4>
                                            <p className="text-sm text-blue-700">
                                                Adapte as cláusulas conforme sua necessidade, mas sempre mantendo
                                                os elementos obrigatórios por lei.
                                            </p>
                                        </div>
                                    </CardContent>
                                </Card>
                            </div>

                            <div className="space-y-6">
                                <Card>
                                    <CardHeader>
                                        <CardTitle className="text-lg">Downloads</CardTitle>
                                    </CardHeader>
                                    <CardContent className="space-y-4">
                                        <Button className="w-full bg-[#e56b4e] hover:bg-[#e56b4e]/90">
                                            <Download className="mr-2 h-4 w-4" />
                                            Modelo Word (.docx)
                                        </Button>
                                        <Button variant="outline" className="w-full">
                                            <Download className="mr-2 h-4 w-4" />
                                            Modelo PDF
                                        </Button>
                                    </CardContent>
                                </Card>

                                <Card>
                                    <CardHeader>
                                        <CardTitle className="text-lg">Guias Relacionados</CardTitle>
                                    </CardHeader>
                                    <CardContent className="space-y-3">
                                        <Link href="/guides/contrato-aluguel" className="block text-sm text-[#e56b4e] hover:underline">
                                            → O que deve constar no contrato
                                        </Link>
                                        <Link href="/guides/lei-inquilinato" className="block text-sm text-[#e56b4e] hover:underline">
                                            → Lei do Inquilinato
                                        </Link>
                                        <Link href="/guides/vistoria" className="block text-sm text-[#e56b4e] hover:underline">
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

export default ContractModelPage;