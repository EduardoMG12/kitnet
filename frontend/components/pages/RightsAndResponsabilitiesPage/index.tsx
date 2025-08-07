import React from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { ArrowLeft, Scale, BookOpen } from "lucide-react";
import MainLayout from "@/components/layout/MainLayout";
import { Breadcrumb, BreadcrumbItem, BreadcrumbLink, BreadcrumbList, BreadcrumbPage, BreadcrumbSeparator } from "@/components/ui/breadcrumb";
import Link from "next/link";

const RightsAndResponsabilitiesPage = () => {
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
                            <BreadcrumbPage>Lei do Inquilinato</BreadcrumbPage>
                        </BreadcrumbList>
                    </Breadcrumb>

                    <div className="max-w-4xl mx-auto">
                        <header className="mb-8">
                            <h1 className="text-3xl md:text-4xl font-heading font-bold mb-4">
                                Lei do Inquilinato (Lei 8.245/91)
                            </h1>
                            <p className="text-lg text-muted-foreground">
                                Guia completo sobre a lei que regula as locações urbanas no Brasil
                            </p>
                        </header>

                        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
                            <div className="lg:col-span-2 space-y-8">
                                <Card>
                                    <CardHeader>
                                        <CardTitle className="flex items-center gap-2">
                                            <Scale className="h-5 w-5 text-blue-600" />
                                            Principais Pontos da Lei
                                        </CardTitle>
                                    </CardHeader>
                                    <CardContent className="space-y-4">
                                        <div>
                                            <h3 className="font-medium mb-2">Direitos do Locatário</h3>
                                            <ul className="text-sm text-muted-foreground space-y-1 ml-4">
                                                <li>• Uso pacífico do imóvel</li>
                                                <li>• Receber o imóvel em bom estado</li>
                                                <li>• Direito à renovação (casos específicos)</li>
                                                <li>• Indenização por benfeitorias necessárias</li>
                                            </ul>
                                        </div>

                                        <div>
                                            <h3 className="font-medium mb-2">Deveres do Locatário</h3>
                                            <ul className="text-sm text-muted-foreground space-y-1 ml-4">
                                                <li>• Pagar aluguel pontualmente</li>
                                                <li>• Conservar o imóvel</li>
                                                <li>• Permitir vistorias</li>
                                                <li>• Devolver o imóvel no mesmo estado</li>
                                            </ul>
                                        </div>

                                        <div>
                                            <h3 className="font-medium mb-2">Direitos do Locador</h3>
                                            <ul className="text-sm text-muted-foreground space-y-1 ml-4">
                                                <li>• Receber aluguel pontualmente</li>
                                                <li>• Vistoriar o imóvel (24h de antecedência)</li>
                                                <li>• Retomar o imóvel ao fim do contrato</li>
                                                <li>• Reajustar o aluguel anualmente</li>
                                            </ul>
                                        </div>
                                    </CardContent>
                                </Card>

                                <Card>
                                    <CardHeader>
                                        <CardTitle>Tipos de Contrato</CardTitle>
                                    </CardHeader>
                                    <CardContent className="space-y-4">
                                        <div>
                                            <h3 className="font-medium mb-2">Prazo Determinado</h3>
                                            <p className="text-sm text-muted-foreground">
                                                Até 30 meses. Não pode ser rescindido antes do prazo,
                                                exceto se houver cláusula específica.
                                            </p>
                                        </div>

                                        <div>
                                            <h3 className="font-medium mb-2">Prazo Indeterminado</h3>
                                            <p className="text-sm text-muted-foreground">
                                                Pode ser rescindido a qualquer momento com aviso prévio
                                                de 30 dias.
                                            </p>
                                        </div>
                                    </CardContent>
                                </Card>

                                <Card>
                                    <CardHeader>
                                        <CardTitle>Reajuste de Aluguel</CardTitle>
                                    </CardHeader>
                                    <CardContent className="space-y-4">
                                        <div className="bg-yellow-50 p-4 rounded-lg border-l-4 border-yellow-400">
                                            <h4 className="font-medium text-yellow-800 mb-2">📊 Índices Permitidos</h4>
                                            <p className="text-sm text-yellow-700">
                                                IGP-M, IPCA, INPC são os índices mais utilizados.
                                                O reajuste só pode ocorrer após 12 meses.
                                            </p>
                                        </div>
                                    </CardContent>
                                </Card>
                            </div>

                            <div className="space-y-6">
                                <Card>
                                    <CardHeader>
                                        <CardTitle className="text-lg">Lei Completa</CardTitle>
                                    </CardHeader>
                                    <CardContent>
                                        <p className="text-sm text-muted-foreground mb-4">
                                            Acesse o texto completo da Lei 8.245/91
                                        </p>
                                        <Button className="w-full bg-[#e56b4e] hover:bg-[#e56b4e]/90">
                                            <BookOpen className="mr-2 h-4 w-4" />
                                            Ler Lei Completa
                                        </Button>
                                    </CardContent>
                                </Card>

                                <Card>
                                    <CardHeader>
                                        <CardTitle className="text-lg">Artigos Relacionados</CardTitle>
                                    </CardHeader>
                                    <CardContent className="space-y-3">
                                        <Link href="/guides/contrato-aluguel" className="block text-sm text-[#e56b4e] hover:underline">
                                            → Contrato de Aluguel
                                        </Link>
                                        <Link href="/guides/quebra-contrato" className="block text-sm text-[#e56b4e] hover:underline">
                                            → Quebra de Contrato
                                        </Link>
                                        <Link href="/guides/modelo-contrato" className="block text-sm text-[#e56b4e] hover:underline">
                                            → Modelo de Contrato
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

export default RightsAndResponsabilitiesPage;