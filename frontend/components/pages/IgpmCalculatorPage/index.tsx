'use client';

import React, { useState } from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { ArrowLeft, Calculator, TrendingUp } from "lucide-react";
import MainLayout from "@/components/layout/MainLayout";
import Link from "next/link";

const IgpmCalculatorPage = () => {
    const [valorInicial, setValorInicial] = useState("");
    const [igpmPercent, setIgpmPercent] = useState("");
    const [resultado, setResultado] = useState<{
        valorInicial: number;
        valorReajustado: number;
        diferenca: number;
        percentual: number;
    } | null>(null);

    const calcularReajuste = () => {
        const valor = Number.parseFloat(valorInicial);
        const igpm = Number.parseFloat(igpmPercent);

        // Validação: garante que os valores são números e que o valor inicial é positivo.
        if (Number.isNaN(valor) || Number.isNaN(igpm) || valor <= 0) {
            setResultado(null); // Limpa o resultado se a validação falhar.
            return;
        }

        // A lógica do cálculo está correta, mesmo com IGP-M negativo (deflação)
        const valorReajustado = valor * (1 + igpm / 100);
        const diferenca = valorReajustado - valor;

        setResultado({
            valorInicial: valor,
            valorReajustado: valorReajustado,
            diferenca: diferenca,
            percentual: igpm
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
                        <h1 className="text-4xl font-heading font-bold mb-4">Calculadora IGP-M</h1>
                        <p className="text-xl text-muted-foreground">
                            Calcule o reajuste do aluguel pelo IGP-M
                        </p>
                    </div>

                    <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
                        <div className="lg:col-span-2 space-y-8">
                            <Card>
                                <CardHeader>
                                    <CardTitle className="flex items-center gap-2">
                                        <Calculator className="h-5 w-5 text-[#e56b4e]" />
                                        Calculadora de Reajuste
                                    </CardTitle>
                                    <CardDescription>
                                        Calcule o novo valor de um contrato com base no índice de preços do mercado.
                                    </CardDescription>
                                </CardHeader>
                                <CardContent className="space-y-6">
                                    <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                                        <div>
                                            <Label htmlFor="valor">Valor Atual do Aluguel</Label>
                                            <Input
                                                id="valor"
                                                type="number"
                                                placeholder="R$ 1.500,00"
                                                value={valorInicial}
                                                onChange={(e) => setValorInicial(e.target.value)}
                                            />
                                        </div>
                                        <div>
                                            <Label htmlFor="igpm">IGP-M Acumulado (%)</Label>
                                            <Input
                                                id="igpm"
                                                type="number"
                                                placeholder="12,20"
                                                step="0.01"
                                                value={igpmPercent}
                                                onChange={(e) => setIgpmPercent(e.target.value)}
                                            />
                                        </div>
                                    </div>

                                    <Button
                                        onClick={calcularReajuste}
                                        className="w-full"
                                        disabled={!valorInicial || !igpmPercent}
                                    >
                                        Calcular Reajuste
                                    </Button>

                                    {resultado && (
                                        <div className="mt-6 p-6 bg-[#e56b4e]/10 rounded-lg">
                                            <h3 className="font-semibold mb-4">Resultado do Cálculo</h3>
                                            <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                                                <div>
                                                    <p className="text-sm text-muted-foreground">Valor Atual</p>
                                                    <p className="text-xl font-bold">
                                                        R$ {resultado.valorInicial.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}
                                                    </p>
                                                </div>
                                                <div>
                                                    <p className="text-sm text-muted-foreground">Valor Reajustado</p>
                                                    <p className="text-xl font-bold text-[#e56b4e]">
                                                        R$ {resultado.valorReajustado.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}
                                                    </p>
                                                </div>
                                                <div>
                                                    <p className="text-sm text-muted-foreground">Diferença</p>
                                                    <p className={`text-xl font-bold ${resultado.diferenca >= 0 ? 'text-green-600' : 'text-red-600'}`}>
                                                        {resultado.diferenca >= 0 ? '+' : ''} R$ {resultado.diferenca.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                    )}
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle className="flex items-center gap-2">
                                        <TrendingUp className="h-5 w-5 text-[#e56b4e]" />
                                        O que é o IGP-M?
                                    </CardTitle>
                                </CardHeader>
                                <CardContent className="space-y-4">
                                    <p className="text-muted-foreground">
                                        O IGP-M (Índice Geral de Preços do Mercado) é um indicador econômico calculado
                                        mensalmente pela Fundação Getúlio Vargas (FGV). É amplamente utilizado para
                                        reajustar valores de contratos, incluindo aluguéis.
                                    </p>

                                    <h3 className="font-semibold">Como funciona:</h3>
                                    <ul className="space-y-2 text-muted-foreground">
                                        <li>• Calculado do dia 21 do mês anterior ao dia 20 do mês de referência</li>
                                        <li>• Considera preços no atacado (60%), varejo (30%) e construção civil (10%)</li>
                                        <li>• Reajuste geralmente aplicado na data de aniversário do contrato</li>
                                        <li>• Pode ser limitado por teto estabelecido em lei ou contrato</li>
                                    </ul>
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle>IGP-M Recente</CardTitle>
                                </CardHeader>
                                <CardContent>
                                    <p className="text-muted-foreground mb-4">
                                        Variação do IGP-M nos últimos 12 meses (valores de exemplo):
                                    </p>
                                    <div className="grid grid-cols-2 md:grid-cols-4 gap-4 text-sm">
                                        <div className="p-3 bg-gray-50 rounded">
                                            <p className="font-medium">Jan/2024</p>
                                            <p className="text-[#e56b4e]">0,78%</p>
                                        </div>
                                        <div className="p-3 bg-gray-50 rounded">
                                            <p className="font-medium">Fev/2024</p>
                                            <p className="text-[#e56b4e]">0,45%</p>
                                        </div>
                                        <div className="p-3 bg-gray-50 rounded">
                                            <p className="font-medium">Mar/2024</p>
                                            <p className="text-[#e56b4e]">0,92%</p>
                                        </div>
                                        <div className="p-3 bg-gray-50 rounded">
                                            <p className="font-medium">Acumulado</p>
                                            <p className="text-[#e56b4e] font-bold">12,20%</p>
                                        </div>
                                    </div>
                                </CardContent>
                            </Card>
                        </div>

                        <div className="space-y-6">
                            <Card>
                                <CardHeader>
                                    <CardTitle className="text-lg">Outras Calculadoras</CardTitle>
                                </CardHeader>
                                <CardContent className="space-y-3">
                                    <Link href="/tools/calculadora-ipca" className="block text-sm text-[#e56b4e] hover:underline">
                                        → Calculadora IPCA
                                    </Link>
                                    <Link href="/tools/calculadora-aluguel" className="block text-sm text-[#e56b4e] hover:underline">
                                        → Calculadora de aluguel
                                    </Link>
                                    <Link href="/tools/comprar-ou-alugar" className="block text-sm text-[#e56b4e] hover:underline">
                                        → Comprar ou alugar?
                                    </Link>
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle className="text-lg">Informações Importantes</CardTitle>
                                </CardHeader>
                                <CardContent className="space-y-3">
                                    <div className="p-3 bg-amber-50 border border-amber-200 rounded-lg">
                                        <p className="text-sm font-medium text-amber-800">⚠️ Atenção</p>
                                        <p className="text-sm text-amber-700">
                                            Verifique sempre o índice oficial no site da FGV
                                        </p>
                                    </div>
                                    <div className="p-3 bg-blue-50 border border-blue-200 rounded-lg">
                                        <p className="text-sm font-medium text-blue-800">💡 Dica</p>
                                        <p className="text-sm text-blue-700">
                                            O reajuste pode ser negociado entre as partes
                                        </p>
                                    </div>
                                </CardContent>
                            </Card>
                        </div>
                    </div>
                </div>
            </div>
        </MainLayout>
    );
};

export default IgpmCalculatorPage;
