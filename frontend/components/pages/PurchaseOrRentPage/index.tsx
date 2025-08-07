'use client';

import React, { useState } from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { ArrowLeft, Calculator, Home, TrendingUp } from "lucide-react";
import MainLayout from "@/components/layout/MainLayout";
import Link from "next/link";

const PurchaseOrRentPage = () => {
    // Estados para os inputs do usuário (mantidos como no código anterior)
    const [valorImovel, setValorImovel] = useState("");
    const [valorAluguel, setValorAluguel] = useState("");
    const [valorEntrada, setValorEntrada] = useState("");
    const [prazoFinanciamento, setPrazoFinanciamento] = useState("30");

    // Estado para o resultado
    const [resultado, setResultado] = useState<{
        prestacao: number;
        custoTotalCompra: number;
        custoTotalAluguel: number;
        multiplicador: number;
        economia: number;
        recomendacao: 'comprar' | 'alugar';
    } | null>(null);

    const calcularComparacao = () => {
        const precoImovel = Number.parseFloat(valorImovel);
        const aluguel = Number.parseFloat(valorAluguel);
        const entrada = Number.parseFloat(valorEntrada) || 0;
        const prazo = Number.parseInt(prazoFinanciamento);

        if (!precoImovel || !aluguel || !prazo) return;

        // Validação: Garante que o valor da entrada não é maior que o valor do imóvel.
        const entradaValidada = Math.min(entrada, precoImovel);

        // Cálculos simplificados
        const financiado = precoImovel - entradaValidada;
        const taxaJuros = 0.009; // 0.9% ao mês (fixo para simplificação)
        const meses = prazo * 12;

        const prestacao = financiado > 0 ? (financiado * (taxaJuros * (1 + taxaJuros) ** meses) / ((1 + taxaJuros) ** meses - 1)) : 0;

        const custoTotalCompra = entradaValidada + (prestacao * meses);
        const custoTotalAluguel = aluguel * meses;

        // Múltiplo aluguel/preço
        const multiplicador = precoImovel / (aluguel * 12);

        const economia = custoTotalAluguel - custoTotalCompra;

        setResultado({
            prestacao: prestacao,
            custoTotalCompra: custoTotalCompra,
            custoTotalAluguel: custoTotalAluguel,
            multiplicador: multiplicador,
            economia: economia,
            recomendacao: economia > 0 ? 'comprar' : 'alugar' // Recomenda comprar se economizar no longo prazo
        });
    };

    return (
        <MainLayout>
            <div className="py-16 bg-gradient-to-b from-[#f9f4e8] to-white">
                <div className="px-4 mx-auto max-w-7xl">
                    <div className="mb-8">
                        <Link href="/guides" className="inline-flex items-center text-[#e56b4e] hover:underline mb-4">
                            <ArrowLeft className="mr-2 h-4 w-4" />
                            Voltar aos Guias
                        </Link>
                        <h1 className="text-4xl font-heading font-bold mb-4">Comprar ou Alugar?</h1>
                        <p className="text-xl text-muted-foreground">
                            Análise financeira para tomar a melhor decisão
                        </p>
                    </div>

                    <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
                        <div className="lg:col-span-2 space-y-8">
                            <Card>
                                <CardHeader>
                                    <CardTitle className="flex items-center gap-2">
                                        <Calculator className="h-5 w-5 text-[#e56b4e]" />
                                        Simulação Financeira
                                    </CardTitle>
                                    <CardDescription>
                                        Compare o cenário de compra e aluguel em um prazo de {prazoFinanciamento} anos.
                                    </CardDescription>
                                </CardHeader>
                                <CardContent className="space-y-6">
                                    <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                                        <div>
                                            <Label htmlFor="valor-imovel">Valor do Imóvel</Label>
                                            <Input
                                                id="valor-imovel"
                                                type="number"
                                                placeholder="R$ 300.000"
                                                value={valorImovel}
                                                onChange={(e) => setValorImovel(e.target.value)}
                                            />
                                        </div>
                                        <div>
                                            <Label htmlFor="valor-aluguel">Aluguel Mensal</Label>
                                            <Input
                                                id="valor-aluguel"
                                                type="number"
                                                placeholder="R$ 1.500"
                                                value={valorAluguel}
                                                onChange={(e) => setValorAluguel(e.target.value)}
                                            />
                                        </div>
                                        <div>
                                            <Label htmlFor="entrada">Valor da Entrada</Label>
                                            <Input
                                                id="entrada"
                                                type="number"
                                                placeholder="R$ 60.000"
                                                value={valorEntrada}
                                                onChange={(e) => setValorEntrada(e.target.value)}
                                            />
                                        </div>
                                        <div>
                                            <Label htmlFor="prazo">Prazo (anos)</Label>
                                            <Input
                                                id="prazo"
                                                type="number"
                                                placeholder="30"
                                                value={prazoFinanciamento}
                                                onChange={(e) => setPrazoFinanciamento(e.target.value)}
                                            />
                                        </div>
                                    </div>

                                    <Button
                                        onClick={calcularComparacao}
                                        className="w-full"
                                        disabled={!valorImovel || !valorAluguel}
                                    >
                                        Calcular Análise
                                    </Button>

                                    {resultado && (
                                        <div className="mt-6 space-y-4">
                                            <div className="p-6 bg-[#e56b4e]/10 rounded-lg">
                                                <h3 className="font-semibold mb-4">Análise Comparativa em {prazoFinanciamento} Anos</h3>
                                                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                                                    <div className="p-4 bg-white rounded border">
                                                        <div className="flex items-center gap-2 mb-2">
                                                            <Home className="h-5 w-5 text-blue-600" />
                                                            <h4 className="font-semibold">Custo de Compra</h4>
                                                        </div>
                                                        <p className="text-sm text-muted-foreground">Prestação mensal</p>
                                                        <p className="text-lg font-bold">
                                                            R$ {resultado.prestacao.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}
                                                        </p>
                                                        <p className="text-sm text-muted-foreground mt-2">Custo total</p>
                                                        <p className="text-2xl font-bold text-[#e56b4e]">
                                                            R$ {resultado.custoTotalCompra.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}
                                                        </p>
                                                    </div>
                                                    <div className="p-4 bg-white rounded border">
                                                        <div className="flex items-center gap-2 mb-2">
                                                            <TrendingUp className="h-5 w-5 text-green-600" />
                                                            <h4 className="font-semibold">Custo de Aluguel</h4>
                                                        </div>
                                                        <p className="text-sm text-muted-foreground">Aluguel mensal</p>
                                                        <p className="text-lg font-bold">
                                                            R$ {Number.parseFloat(valorAluguel).toLocaleString('pt-BR', { minimumFractionDigits: 2 })}
                                                        </p>
                                                        <p className="text-sm text-muted-foreground mt-2">Custo total</p>
                                                        <p className="text-2xl font-bold text-[#e56b4e]">
                                                            R$ {resultado.custoTotalAluguel.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}
                                                        </p>
                                                    </div>
                                                </div>
                                            </div>

                                            <div className={`p-4 rounded-lg ${resultado.recomendacao === 'comprar' ? 'bg-green-50 border border-green-200' : 'bg-blue-50 border border-blue-200'}`}>
                                                <h3 className="font-semibold mb-2">💡 Recomendação</h3>
                                                <p className={`${resultado.recomendacao === 'comprar' ? 'text-green-800' : 'text-blue-800'}`}>
                                                    {resultado.recomendacao === 'comprar'
                                                        ? 'Comprar pode ser mais vantajoso no longo prazo'
                                                        : 'Alugar pode ser mais vantajoso financeiramente'}
                                                </p>
                                                <p className="text-sm text-muted-foreground mt-2">
                                                    Diferença de custo total: R$ {Math.abs(resultado.economia).toLocaleString('pt-BR', { minimumFractionDigits: 2 })}
                                                </p>
                                            </div>
                                        </div>
                                    )}
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle>Fatores a Considerar</CardTitle>
                                </CardHeader>
                                <CardContent className="space-y-4">
                                    <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                                        <div>
                                            <h3 className="font-semibold text-green-700 mb-3">Vantagens de Comprar</h3>
                                            <ul className="space-y-2 text-sm text-muted-foreground">
                                                <li>• Patrimônio próprio</li>
                                                <li>• Proteção contra inflação</li>
                                                <li>• Liberdade para reformas</li>
                                                <li>• Segurança habitacional</li>
                                                <li>• Possível valorização</li>
                                            </ul>
                                        </div>
                                        <div>
                                            <h3 className="font-semibold text-blue-700 mb-3">Vantagens de Alugar</h3>
                                            <ul className="space-y-2 text-sm text-muted-foreground">
                                                <li>• Maior flexibilidade</li>
                                                <li>• Menos responsabilidades</li>
                                                <li>• Capital disponível para investir</li>
                                                <li>• Sem custos de manutenção</li>
                                                <li>• Facilidade para mudar</li>
                                            </ul>
                                        </div>
                                    </div>
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle>Custos Adicionais</CardTitle>
                                </CardHeader>
                                <CardContent>
                                    <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                                        <div>
                                            <h3 className="font-semibold mb-3">Custos de Compra</h3>
                                            <ul className="space-y-2 text-sm text-muted-foreground">
                                                <li>• ITBI (2-3% do valor)</li>
                                                <li>• Registro em cartório</li>
                                                <li>• Avaliação do imóvel</li>
                                                <li>• Seguro do financiamento</li>
                                                <li>• Manutenção e reparos</li>
                                                <li>• IPTU</li>
                                            </ul>
                                        </div>
                                        <div>
                                            <h3 className="font-semibold mb-3">Custos de Aluguel</h3>
                                            <ul className="space-y-2 text-sm text-muted-foreground">
                                                <li>• Depósito caução</li>
                                                <li>• Taxa de administração</li>
                                                <li>• Seguro fiança</li>
                                                <li>• Mudanças</li>
                                                <li>• Reajustes anuais</li>
                                            </ul>
                                        </div>
                                    </div>
                                </CardContent>
                            </Card>
                        </div>

                        <div className="space-y-6">
                            <Card>
                                <CardHeader>
                                    <CardTitle className="text-lg">Ferramentas Relacionadas</CardTitle>
                                </CardHeader>
                                <CardContent className="space-y-3">
                                    <Link href="/tools/valor-imovel" className="block text-sm text-[#e56b4e] hover:underline">
                                        → Quanto vale meu imóvel?
                                    </Link>
                                    <Link href="/tools/aluguel-orcamento" className="block text-sm text-[#e56b4e] hover:underline">
                                        → Que aluguel cabe no meu bolso?
                                    </Link>
                                    <Link href="/tools/calculadora-aluguel" className="block text-sm text-[#e56b4e] hover:underline">
                                        → Calculadora de aluguel
                                    </Link>
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle className="text-lg">Análise Personalizada</CardTitle>
                                </CardHeader>
                                <CardContent>
                                    <p className="text-sm text-muted-foreground mb-4">
                                        Precisa de uma análise mais detalhada? Nossos especialistas podem ajudar.
                                    </p>
                                    <Button className="w-full">
                                        Consulta Especializada
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

export default PurchaseOrRentPage;
