import React from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { ArrowLeft, MapPin, FileCheck, Key } from "lucide-react";
import MainLayout from "@/components/layout/MainLayout";
import Link from "next/link";

const HowToRentPage = () => {
    return (
        <MainLayout>
            <div className="py-16 bg-gradient-to-b from-[#f9f4e8] to-white">
                <div className="px-4 mx-auto max-w-7xl">
                    <div className="mb-8">
                        <Link href="/guides" className="inline-flex items-center text-[#e56b4e] hover:underline mb-4">
                            <ArrowLeft className="mr-2 h-4 w-4" />
                            Voltar aos Guias
                        </Link>
                        <h1 className="text-4xl font-heading font-bold mb-4">Como Alugar um Imóvel</h1>
                        <p className="text-xl text-muted-foreground">
                            Passo a passo completo para alugar seu primeiro imóvel
                        </p>
                    </div>

                    <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
                        <div className="lg:col-span-2 space-y-8">
                            <Card>
                                <CardHeader>
                                    <CardTitle className="flex items-center gap-2">
                                        <MapPin className="h-5 w-5 text-[#e56b4e]" />
                                        Passo 1: Pesquisa e Localização
                                    </CardTitle>
                                </CardHeader>
                                <CardContent className="space-y-4">
                                    <h3 className="font-semibold">Define seu orçamento</h3>
                                    <p className="text-muted-foreground mb-4">
                                        O aluguel não deve ultrapassar 30% da sua renda familiar bruta.
                                    </p>
                                    <h3 className="font-semibold">Escolha a região</h3>
                                    <ul className="space-y-2 text-muted-foreground">
                                        <li>• Proximidade ao trabalho</li>
                                        <li>• Transporte público</li>
                                        <li>• Segurança da região</li>
                                        <li>• Comércio e serviços</li>
                                        <li>• Escolas (se tiver filhos)</li>
                                    </ul>
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle className="flex items-center gap-2">
                                        <FileCheck className="h-5 w-5 text-[#e56b4e]" />
                                        Passo 2: Documentação
                                    </CardTitle>
                                </CardHeader>
                                <CardContent>
                                    <h3 className="font-semibold mb-3">Documentos necessários:</h3>
                                    <ul className="space-y-2 text-muted-foreground">
                                        <li>• RG e CPF</li>
                                        <li>• Comprovante de renda (3 últimos meses)</li>
                                        <li>• Comprovante de residência</li>
                                        <li>• Referências pessoais e comerciais</li>
                                        <li>• Certidão de nascimento/casamento</li>
                                        <li>• Extrato bancário</li>
                                    </ul>
                                    <Link href="/guides/documentos-necessarios" className="inline-block mt-4 text-[#e56b4e] hover:underline">
                                        → Ver lista completa de documentos
                                    </Link>
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle className="flex items-center gap-2">
                                        <Key className="h-5 w-5 text-[#e56b4e]" />
                                        Passo 3: Vistoria e Contrato
                                    </CardTitle>
                                </CardHeader>
                                <CardContent>
                                    <h3 className="font-semibold mb-3">Antes de assinar:</h3>
                                    <ul className="space-y-2 text-muted-foreground">
                                        <li>• Faça vistoria detalhada do imóvel</li>
                                        <li>• Verifique todas as instalações</li>
                                        <li>• Teste torneiras, chuveiros, tomadas</li>
                                        <li>• Fotografe eventuais problemas</li>
                                        <li>• Leia todo o contrato cuidadosamente</li>
                                        <li>• Negocie cláusulas se necessário</li>
                                    </ul>
                                    <Link href="/guides/vistoria" className="inline-block mt-4 text-[#e56b4e] hover:underline">
                                        → Guia completo de vistoria
                                    </Link>
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle>Custos iniciais</CardTitle>
                                </CardHeader>
                                <CardContent>
                                    <p className="mb-4">
                                        Prepare-se para os custos iniciais do aluguel:
                                    </p>
                                    <ul className="space-y-2 text-muted-foreground">
                                        <li>• 1º aluguel (adiantado)</li>
                                        <li>• Depósito caução (1-3 aluguéis)</li>
                                        <li>• Taxa de administração</li>
                                        <li>• Seguro fiança (se aplicável)</li>
                                        <li>• IPTU (se incluído)</li>
                                        <li>• Condomínio (se aplicável)</li>
                                    </ul>
                                </CardContent>
                            </Card>
                        </div>

                        <div className="space-y-6">
                            <Card>
                                <CardHeader>
                                    <CardTitle className="text-lg">Ferramentas Úteis</CardTitle>
                                </CardHeader>
                                <CardContent className="space-y-3">
                                    <Link href="/tools/aluguel-orcamento" className="block text-sm text-[#e56b4e] hover:underline">
                                        → Que aluguel cabe no meu bolso?
                                    </Link>
                                    <Link href="/tools/calculadora-aluguel" className="block text-sm text-[#e56b4e] hover:underline">
                                        → Calculadora de aluguel
                                    </Link>
                                    <Link href="/properties" className="block text-sm text-[#e56b4e] hover:underline">
                                        → Ver imóveis disponíveis
                                    </Link>
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle className="text-lg">Precisa de Ajuda?</CardTitle>
                                </CardHeader>
                                <CardContent>
                                    <p className="text-sm text-muted-foreground mb-4">
                                        Nossa equipe pode te ajudar em todo o processo
                                    </p>
                                    <Button className="w-full">
                                        Falar com Especialista
                                    </Button>
                                </CardContent>
                            </Card>
                        </div>
                    </div>
                </div>
            </div>
        </MainLayout>
    );
};

export default HowToRentPage;