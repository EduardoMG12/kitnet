import React from "react";

import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { ArrowRight, BookOpen, Calculator, FileText, Building, Scale, TrendingUp } from "lucide-react";
import MainLayout from "@/components/layout/MainLayout";
import Link from "next/link";

const WelcomeGuidePage = () => {
    return (
        <MainLayout>
            <div className="py-16 bg-gradient-to-b from-[#f9f4e8] to-white">
                <div className="px-4 mx-auto max-w-7xl">
                    <div className="text-center mb-12">
                        <h1 className="text-4xl md:text-5xl font-heading font-bold mb-6">
                            Guias KitNet
                        </h1>
                        <p className="text-xl text-muted-foreground max-w-3xl mx-auto">
                            Tudo que você precisa saber sobre o mercado imobiliário.
                            Aprenda sobre contratos, legislação, calculadoras e muito mais.
                        </p>
                    </div>

                    <Tabs defaultValue="guides" className="w-full">
                        <TabsList className="grid w-full grid-cols-3 mb-8">
                            <TabsTrigger value="guides">Guias e Manuais</TabsTrigger>
                            <TabsTrigger value="tools">Ferramentas</TabsTrigger>
                            <TabsTrigger value="market">Mercado</TabsTrigger>
                        </TabsList>

                        <TabsContent value="guides">
                            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                                <Card className="hover:shadow-lg transition-shadow">
                                    <CardHeader>
                                        <div className="bg-[#e56b4e]/10 p-2 rounded-lg w-fit mb-2">
                                            <FileText className="h-6 w-6 text-[#e56b4e]" />
                                        </div>
                                        <CardTitle className="text-lg">Contratos de Aluguel</CardTitle>
                                        <CardDescription>
                                            Tudo sobre contratos, modelos e o que deve constar
                                        </CardDescription>
                                    </CardHeader>
                                    <CardContent>
                                        <div className="space-y-2">
                                            <Link href="/guides/contrato-aluguel" className="block text-sm text-[#e56b4e] hover:underline">
                                                → O que deve constar em um contrato
                                            </Link>
                                            <Link href="/guides/modelo-contrato" className="block text-sm text-[#e56b4e] hover:underline">
                                                → Modelo de contrato para download
                                            </Link>
                                            <Link href="/guides/quebra-contrato" className="block text-sm text-[#e56b4e] hover:underline">
                                                → Quebra de contrato de aluguel
                                            </Link>
                                        </div>
                                    </CardContent>
                                </Card>

                                <Card className="hover:shadow-lg transition-shadow">
                                    <CardHeader>
                                        <div className="bg-[#e56b4e]/10 p-2 rounded-lg w-fit mb-2">
                                            <Scale className="h-6 w-6 text-[#e56b4e]" />
                                        </div>
                                        <CardTitle className="text-lg">Legislação</CardTitle>
                                        <CardDescription>
                                            Leis e regulamentações do mercado imobiliário
                                        </CardDescription>
                                    </CardHeader>
                                    <CardContent>
                                        <div className="space-y-2">
                                            <Link href="/guides/lei-inquilinato" className="block text-sm text-[#e56b4e] hover:underline">
                                                → Lei do Inquilinato completa
                                            </Link>
                                            <Link href="/guides/direitos-inquilino" className="block text-sm text-[#e56b4e] hover:underline">
                                                → Direitos do inquilino
                                            </Link>
                                            <Link href="/guides/direitos-proprietario" className="block text-sm text-[#e56b4e] hover:underline">
                                                → Direitos do proprietário
                                            </Link>
                                        </div>
                                    </CardContent>
                                </Card>

                                <Card className="hover:shadow-lg transition-shadow">
                                    <CardHeader>
                                        <div className="bg-[#e56b4e]/10 p-2 rounded-lg w-fit mb-2">
                                            <Building className="h-6 w-6 text-[#e56b4e]" />
                                        </div>
                                        <CardTitle className="text-lg">Condomínio</CardTitle>
                                        <CardDescription>
                                            Guias sobre vida em condomínio e taxas
                                        </CardDescription>
                                    </CardHeader>
                                    <CardContent>
                                        <div className="space-y-2">
                                            <Link href="/guides/taxa-condominio" className="block text-sm text-[#e56b4e] hover:underline">
                                                → Entendendo taxas de condomínio
                                            </Link>
                                            <Link href="/guides/regras-condominio" className="block text-sm text-[#e56b4e] hover:underline">
                                                → Regras de condomínio
                                            </Link>
                                            <Link href="/guides/assembleia" className="block text-sm text-[#e56b4e] hover:underline">
                                                → Como funcionam as assembleias
                                            </Link>
                                        </div>
                                    </CardContent>
                                </Card>

                                <Card className="hover:shadow-lg transition-shadow">
                                    <CardHeader>
                                        <div className="bg-[#e56b4e]/10 p-2 rounded-lg w-fit mb-2">
                                            <BookOpen className="h-6 w-6 text-[#e56b4e]" />
                                        </div>
                                        <CardTitle className="text-lg">Manual Imobiliário</CardTitle>
                                        <CardDescription>
                                            Guia completo para alugar e comprar imóveis
                                        </CardDescription>
                                    </CardHeader>
                                    <CardContent>
                                        <div className="space-y-2">
                                            <Link href="/guides/como-alugar" className="block text-sm text-[#e56b4e] hover:underline">
                                                → Como alugar um imóvel
                                            </Link>
                                            <Link href="/guides/documentos-necessarios" className="block text-sm text-[#e56b4e] hover:underline">
                                                → Documentos necessários
                                            </Link>
                                            <Link href="/guides/vistoria" className="block text-sm text-[#e56b4e] hover:underline">
                                                → Como fazer vistoria
                                            </Link>
                                        </div>
                                    </CardContent>
                                </Card>
                            </div>
                        </TabsContent>

                        <TabsContent value="tools">
                            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                                <Card className="hover:shadow-lg transition-shadow">
                                    <CardHeader>
                                        <div className="bg-[#e56b4e]/10 p-2 rounded-lg w-fit mb-2">
                                            <Calculator className="h-6 w-6 text-[#e56b4e]" />
                                        </div>
                                        <CardTitle className="text-lg">Calculadoras</CardTitle>
                                        <CardDescription>
                                            Ferramentas para calcular valores e índices
                                        </CardDescription>
                                    </CardHeader>
                                    <CardContent>
                                        <div className="space-y-2">
                                            <Link href="/tools/calculadora-aluguel" className="block text-sm text-[#e56b4e] hover:underline">
                                                → Calculadora de aluguel
                                            </Link>
                                            <Link href="/tools/calculadora-igpm" className="block text-sm text-[#e56b4e] hover:underline">
                                                → Calculadora IGP-M
                                            </Link>
                                            <Link href="/tools/calculadora-ipca" className="block text-sm text-[#e56b4e] hover:underline">
                                                → Calculadora IPCA
                                            </Link>
                                            <Link href="/tools/comprar-ou-alugar" className="block text-sm text-[#e56b4e] hover:underline">
                                                → Comprar ou Alugar?
                                            </Link>
                                        </div>
                                    </CardContent>
                                </Card>

                                <Card className="hover:shadow-lg transition-shadow">
                                    <CardHeader>
                                        <div className="bg-[#e56b4e]/10 p-2 rounded-lg w-fit mb-2">
                                            <TrendingUp className="h-6 w-6 text-[#e56b4e]" />
                                        </div>
                                        <CardTitle className="text-lg">Avaliação</CardTitle>
                                        <CardDescription>
                                            Descubra o valor do seu imóvel
                                        </CardDescription>
                                    </CardHeader>
                                    <CardContent>
                                        <div className="space-y-2">
                                            <Link href="/tools/valor-imovel" className="block text-sm text-[#e56b4e] hover:underline">
                                                → Quanto vale meu imóvel?
                                            </Link>
                                            <Link href="/tools/quanto-cobrar" className="block text-sm text-[#e56b4e] hover:underline">
                                                → Quanto cobrar de aluguel
                                            </Link>
                                            <Link href="/tools/aluguel-orcamento" className="block text-sm text-[#e56b4e] hover:underline">
                                                → Que aluguel cabe no meu bolso?
                                            </Link>
                                        </div>
                                    </CardContent>
                                </Card>
                            </div>
                        </TabsContent>

                        <TabsContent value="market">
                            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                                <Card className="hover:shadow-lg transition-shadow">
                                    <CardHeader>
                                        <div className="bg-[#e56b4e]/10 p-2 rounded-lg w-fit mb-2">
                                            <TrendingUp className="h-6 w-6 text-[#e56b4e]" />
                                        </div>
                                        <CardTitle className="text-lg">Mercado Imobiliário</CardTitle>
                                        <CardDescription>
                                            Análises e tendências do mercado
                                        </CardDescription>
                                    </CardHeader>
                                    <CardContent>
                                        <div className="space-y-2">
                                            <Link href="/market/tendencias" className="block text-sm text-[#e56b4e] hover:underline">
                                                → Tendências do mercado
                                            </Link>
                                            <Link href="/market/precos-regiao" className="block text-sm text-[#e56b4e] hover:underline">
                                                → Preços por região
                                            </Link>
                                            <Link href="/market/analise-investimento" className="block text-sm text-[#e56b4e] hover:underline">
                                                → Análise de investimento
                                            </Link>
                                        </div>
                                    </CardContent>
                                </Card>

                                <Card className="hover:shadow-lg transition-shadow">
                                    <CardHeader>
                                        <div className="bg-[#e56b4e]/10 p-2 rounded-lg w-fit mb-2">
                                            <Building className="h-6 w-6 text-[#e56b4e]" />
                                        </div>
                                        <CardTitle className="text-lg">Explorar Condomínios</CardTitle>
                                        <CardDescription>
                                            Descubra os melhores condomínios
                                        </CardDescription>
                                    </CardHeader>
                                    <CardContent>
                                        <div className="space-y-2">
                                            <Link href="/market/condominios-luxo" className="block text-sm text-[#e56b4e] hover:underline">
                                                → Condomínios de luxo
                                            </Link>
                                            <Link href="/market/condominios-familia" className="block text-sm text-[#e56b4e] hover:underline">
                                                → Condomínios para família
                                            </Link>
                                            <Link href="/market/condominios-investimento" className="block text-sm text-[#e56b4e] hover:underline">
                                                → Melhores para investimento
                                            </Link>
                                        </div>
                                    </CardContent>
                                </Card>
                            </div>
                        </TabsContent>
                    </Tabs>

                    <div className="mt-16 text-center">
                        <h2 className="text-2xl font-heading font-bold mb-4">
                            Não encontrou o que procurava?
                        </h2>
                        <p className="text-muted-foreground mb-6">
                            Entre em contato conosco ou explore nossos imóveis disponíveis
                        </p>
                        <div className="flex flex-wrap gap-4 justify-center">
                            <Link href="/properties">
                                <Button className="bg-[#e56b4e] hover:bg-[#e56b4e]/90">
                                    Ver Imóveis
                                    <ArrowRight className="ml-2 h-4 w-4" />
                                </Button>
                            </Link>
                            <Button variant="outline">
                                Falar com Especialista
                            </Button>
                        </div>
                    </div>
                </div>
            </div>
        </MainLayout>
    );
};

export default WelcomeGuidePage;