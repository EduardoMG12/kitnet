'use client';

import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { ArrowLeft, Calculator } from "lucide-react";
import MainLayout from "@/components/layout/MainLayout";
import { Breadcrumb, BreadcrumbItem, BreadcrumbLink, BreadcrumbList, BreadcrumbPage, BreadcrumbSeparator } from "@/components/ui/breadcrumb";
import { useState } from "react";
import Link from "next/link";

const RentalCalculatorPage = () => {
    const [renda, setRenda] = useState("");
    const [gastosSugeridos, setGastosSugeridos] = useState(0);
    const [aluguelMaximo, setAluguelMaximo] = useState(0);

    const calcular = () => {
        const rendaFormatada = renda.replace(/[^\d,]/g, '').replace(',', '.');

        const rendaNumero = Number.parseFloat(rendaFormatada) || 0;
        const aluguelSugerido = rendaNumero * 0.3;
        const gastosTotais = aluguelSugerido * 1.4;
        console.log(rendaNumero);
        setAluguelMaximo(aluguelSugerido);
        setGastosSugeridos(gastosTotais);
    };

    const formatarMoeda = (valor: number) => {
        return valor.toLocaleString('pt-BR', {
            style: 'currency',
            currency: 'BRL'
        });
    };

    const handleRendaChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const valor = e.target.value.replace(/\D/g, '');
        const valorFormatado = new Intl.NumberFormat('pt-BR', {
            style: 'currency',
            currency: 'BRL'
        }).format(Number.parseFloat(valor) / 100 || 0);
        setRenda(valorFormatado);
    };

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
                            <BreadcrumbPage>Calculadora de Aluguel</BreadcrumbPage>
                        </BreadcrumbList>
                    </Breadcrumb>

                    <div className="max-w-4xl mx-auto">
                        <header className="mb-8 text-center">
                            <h1 className="text-3xl md:text-4xl font-heading font-bold mb-4">
                                Calculadora de Aluguel
                            </h1>
                            <p className="text-lg text-muted-foreground">
                                Descubra qual valor de aluguel cabe no seu orçamento
                            </p>
                        </header>

                        <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
                            <Card>
                                <CardHeader>
                                    <CardTitle className="flex items-center gap-2">
                                        <Calculator className="h-5 w-5 text-terracotta" />
                                        Calculadora
                                    </CardTitle>
                                </CardHeader>
                                <CardContent className="space-y-6">
                                    <div>
                                        <Label htmlFor="renda">Sua renda mensal líquida</Label>
                                        <Input
                                            id="renda"
                                            type="text"
                                            value={renda}
                                            onChange={handleRendaChange}
                                            placeholder="R$ 0,00"
                                            className="mt-1"
                                        />
                                        <p className="text-xs text-muted-foreground mt-1">
                                            Informe sua renda líquida (após descontos)
                                        </p>
                                    </div>

                                    <Button
                                        onClick={calcular}
                                        className="w-full bg-[#e56b4e] hover:bg-[#e56b4e]/90"
                                        disabled={!renda || renda === "R$ 0,00"}
                                    >
                                        Calcular
                                    </Button>

                                    {aluguelMaximo > 0 && (
                                        <div className="space-y-4 p-4 bg-green-50 rounded-lg">
                                            <h3 className="font-medium text-green-800">Resultado da Análise:</h3>

                                            <div className="space-y-2">
                                                <div className="flex justify-between">
                                                    <span className="text-sm">Aluguel máximo recomendado:</span>
                                                    <span className="font-medium text-green-700">
                                                        {formatarMoeda(aluguelMaximo)}
                                                    </span>
                                                </div>

                                                <div className="flex justify-between">
                                                    <span className="text-sm">Gastos totais estimados:</span>
                                                    <span className="font-medium text-green-700">
                                                        {formatarMoeda(gastosSugeridos)}
                                                    </span>
                                                </div>
                                            </div>

                                            <p className="text-xs text-green-600">
                                                * Incluindo aluguel, condomínio, IPTU e outros custos estimados
                                            </p>
                                        </div>
                                    )}
                                </CardContent>
                            </Card>

                            <div className="space-y-6">
                                <Card>
                                    <CardHeader>
                                        <CardTitle>Como funciona?</CardTitle>
                                    </CardHeader>
                                    <CardContent className="space-y-4 text-sm">
                                        <p>
                                            Nossa calculadora usa a regra dos <strong>30% da renda</strong>,
                                            que é amplamente recomendada por especialistas financeiros.
                                        </p>

                                        <div>
                                            <h4 className="font-medium mb-2">O que está incluído:</h4>
                                            <ul className="space-y-1 text-muted-foreground ml-4">
                                                <li>• Valor do aluguel</li>
                                                <li>• Taxa de condomínio</li>
                                                <li>• IPTU</li>
                                                <li>• Seguro fiança (estimado)</li>
                                            </ul>
                                        </div>
                                    </CardContent>
                                </Card>

                                <Card>
                                    <CardHeader>
                                        <CardTitle>Dicas Importantes</CardTitle>
                                    </CardHeader>
                                    <CardContent className="space-y-4">
                                        <div className="bg-blue-50 p-4 rounded-lg border-l-4 border-blue-400">
                                            <h4 className="font-medium text-blue-800 mb-2">💡 Dica</h4>
                                            <p className="text-sm text-blue-700">
                                                Reserve sempre uma margem de segurança no seu orçamento
                                                para gastos extras e emergências.
                                            </p>
                                        </div>

                                        <div className="bg-amber-50 p-4 rounded-lg border-l-4 border-amber-400">
                                            <h4 className="font-medium text-amber-800 mb-2">⚠️ Atenção</h4>
                                            <p className="text-sm text-amber-700">
                                                Considere também gastos com mudança, depósito caução
                                                e possíveis reformas.
                                            </p>
                                        </div>
                                    </CardContent>
                                </Card>

                                <Card>
                                    <CardHeader>
                                        <CardTitle>Outras Calculadoras</CardTitle>
                                    </CardHeader>
                                    <CardContent className="space-y-3">
                                        <Link href="/tools/comprar-ou-alugar" className="block text-sm text-terracotta hover:underline">
                                            → Comprar ou Alugar?
                                        </Link>
                                        <Link href="/tools/valor-imovel" className="block text-sm text-terracotta hover:underline">
                                            → Quanto vale meu imóvel?
                                        </Link>
                                        <Link href="/tools/calculadora-igpm" className="block text-sm text-terracotta hover:underline">
                                            → Calculadora IGP-M
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

export default RentalCalculatorPage;