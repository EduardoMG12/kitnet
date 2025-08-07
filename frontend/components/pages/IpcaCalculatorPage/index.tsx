'use client';

import React, { useState } from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { ArrowLeft, Calculator, TrendingUp } from "lucide-react";
import MainLayout from "@/components/layout/MainLayout";
import Link from "next/link";

const IpcaCalculatorPage = () => {
    const [valorInicial, setValorInicial] = useState("");
    const [ipcaPercent, setIpcaPercent] = useState("");
    const [resultado, setResultado] = useState<{
        valorInicial: number;
        valorReajustado: number;
        diferenca: number;
        percentual: number;
    } | null>(null);

    const calcularReajuste = () => {
        const valor = Number.parseFloat(valorInicial);
        const ipca = Number.parseFloat(ipcaPercent);

        // Validação: garante que os valores são números e que o valor inicial é positivo.
        if (Number.isNaN(valor) || Number.isNaN(ipca) || valor <= 0) {
            setResultado(null); // Limpa o resultado se a validação falhar.
            return;
        }

        // A lógica do cálculo está correta, mesmo com IPCA negativo (deflação)
        const valorReajustado = valor * (1 + ipca / 100);
        const diferenca = valorReajustado - valor;

        setResultado({
            valorInicial: valor,
            valorReajustado: valorReajustado,
            diferenca: diferenca,
            percentual: ipca
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
                        <h1 className="text-4xl font-heading font-bold mb-4">Calculadora IPCA</h1>
                        <p className="text-xl text-muted-foreground">
                            Calcule o reajuste do aluguel pelo IPCA
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
                                        Calcule o novo valor de um contrato com base na inflação do período.
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
                                            <Label htmlFor="ipca">IPCA Acumulado (%)</Label>
                                            <Input
                                                id="ipca"
                                                type="number"
                                                placeholder="5,90"
                                                step="0.01"
                                                value={ipcaPercent}
                                                onChange={(e) => setIpcaPercent(e.target.value)}
                                            />
                                        </div>
                                    </div>

                                    <Button
                                        onClick={calcularReajuste}
                                        className="w-full"
                                        disabled={!valorInicial || !ipcaPercent}
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
                                        O que é o IPCA?
                                    </CardTitle>
                                </CardHeader>
                                <CardContent className="space-y-4">
                                    <p className="text-muted-foreground">
                                        O IPCA (Índice Nacional de Preços ao Consumidor Amplo) é o índice oficial de
                                        inflação do Brasil, calculado pelo IBGE. É usado como referência para as metas
                                        de inflação e reajustes contratuais.
                                    </p>

                                    <h3 className="font-semibold">Características do IPCA:</h3>
                                    <ul className="space-y-2 text-muted-foreground">
                                        <li>• Calculado mensalmente pelo IBGE</li>
                                        <li>• Reflete a variação de preços para famílias com renda de 1 a 40 salários mínimos</li>
                                        <li>• Abrange alimentação, habitação, transporte, educação, saúde, etc.</li>
                                        <li>• É a inflação oficial do país</li>
                                    </ul>

                                    <h3 className="font-semibold">IPCA vs IGP-M:</h3>
                                    <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                                        <div className="p-3 bg-green-50 border border-green-200 rounded">
                                            <h4 className="font-medium text-green-800">IPCA</h4>
                                            <p className="text-sm text-green-700">Mais estável, reflete o consumo das famílias</p>
                                        </div>
                                        <div className="p-3 bg-blue-50 border border-blue-200 rounded">
                                            <h4 className="font-medium text-blue-800">IGP-M</h4>
                                            <p className="text-sm text-blue-700">Mais volátil, inclui preços no atacado</p>
                                        </div>
                                    </div>
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle>IPCA Recente</CardTitle>
                                </CardHeader>
                                <CardContent>
                                    <p className="text-muted-foreground mb-4">
                                        Variação do IPCA nos últimos 12 meses (valores de exemplo):
                                    </p>
                                    <div className="grid grid-cols-2 md:grid-cols-4 gap-4 text-sm">
                                        <div className="p-3 bg-gray-50 rounded">
                                            <p className="font-medium">Jan/2024</p>
                                            <p className="text-[#e56b4e]">0,42%</p>
                                        </div>
                                        <div className="p-3 bg-gray-50 rounded">
                                            <p className="font-medium">Fev/2024</p>
                                            <p className="text-[#e56b4e]">0,83%</p>
                                        </div>
                                        <div className="p-3 bg-gray-50 rounded">
                                            <p className="font-medium">Mar/2024</p>
                                            <p className="text-[#e56b4e]">0,16%</p>
                                        </div>
                                        <div className="p-3 bg-gray-50 rounded">
                                            <p className="font-medium">Acumulado</p>
                                            <p className="text-[#e56b4e] font-bold">5,90%</p>
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
                                    <Link href="/tools/calculadora-igpm" className="block text-sm text-[#e56b4e] hover:underline">
                                        → Calculadora IGP-M
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
                                            Verifique sempre o índice oficial no site do IBGE
                                        </p>
                                    </div>
                                    <div className="p-3 bg-blue-50 border border-blue-200 rounded-lg">
                                        <p className="text-sm font-medium text-blue-800">💡 Dica</p>
                                        <p className="text-sm text-blue-700">
                                            IPCA costuma ser menor que IGP-M
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

export default IpcaCalculatorPage;
