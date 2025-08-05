'use client';
import React, { useState } from "react";

import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { ArrowLeft, Calculator, DollarSign, TrendingUp } from "lucide-react";
import MainLayout from "@/components/layout/MainLayout";
import Link from "next/link";

const PricingGuidePage = () => {
    const [valorImovel, setValorImovel] = useState("");
    const [area, setArea] = useState("");
    const [quartos, setQuartos] = useState("");
    // Novos estados para o yield esperado e o valor médio do aluguel por m²
    const [yieldEsperado, setYieldEsperado] = useState(""); // Em porcentagem, ex: "4.5" para 4.5%
    const [aluguelM2Base, setAluguelM2Base] = useState(""); // Valor médio do aluguel por m² na região

    const [resultado, setResultado] = useState<{
        aluguelSugerido: number;
        aluguelMin: number;
        aluguelMax: number;
        yieldAnual: number;
        aluguelPorM2: number;
    } | null>(null);

    const calcularAluguel = () => {
        const valor = Number.parseFloat(valorImovel);
        const areaNum = Number.parseFloat(area);
        const yieldEsperadoNum = Number.parseFloat(yieldEsperado);
        const aluguelM2BaseNum = Number.parseFloat(aluguelM2Base);

        // Validação básica: todos os campos principais são obrigatórios
        if (!valor || !areaNum || !quartos || !yieldEsperadoNum || !aluguelM2BaseNum) {
            setResultado(null); // Limpa o resultado se a validação falhar
            return;
        }

        // 1. Cálculo do aluguel baseado no Yield esperado do valor do imóvel
        // Converte o yield de porcentagem para decimal (ex: 4.5% -> 0.045)
        const yieldDecimal = yieldEsperadoNum / 100;
        const aluguelBasePeloValor = (valor * yieldDecimal) / 12;

        // 2. Cálculo do aluguel baseado no valor médio por m² na região
        const aluguelBasePelaArea = areaNum * aluguelM2BaseNum;

        // Média das duas metodologias para um aluguel base mais robusto
        const aluguelBaseCombinado = (aluguelBasePeloValor + aluguelBasePelaArea) / 2;

        // Ajuste por número de quartos (mantido da lógica anterior, pode ser refinado)
        const numQuartos = Number.parseInt(quartos) || 0;
        const fatorQuartos = 1 + (numQuartos - 2) * 0.1; // Ex: 2 quartos = 1; 3 quartos = 1.1; 1 quarto = 0.9

        const aluguelSugerido = aluguelBaseCombinado * fatorQuartos;
        const aluguelMin = aluguelSugerido * 0.9; // Margem de 10% para baixo
        const aluguelMax = aluguelSugerido * 1.1; // Margem de 10% para cima

        // Cálculo do Yield Anual real com base no aluguel sugerido
        const yieldAnualCalculado = (aluguelSugerido * 12 / valor) * 100;
        const aluguelPorM2Calculado = aluguelSugerido / areaNum;

        setResultado({
            aluguelSugerido: aluguelSugerido,
            aluguelMin: aluguelMin,
            aluguelMax: aluguelMax,
            yieldAnual: yieldAnualCalculado,
            aluguelPorM2: aluguelPorM2Calculado
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
                        <h1 className="text-4xl font-heading font-bold mb-4">Quanto Cobrar de Aluguel</h1>
                        <p className="text-xl text-muted-foreground">
                            Calcule o valor ideal para o aluguel do seu imóvel
                        </p>
                    </div>

                    <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
                        <div className="lg:col-span-2 space-y-8">
                            <Card>
                                <CardHeader>
                                    <CardTitle className="flex items-center gap-2">
                                        <Calculator className="h-5 w-5 text-[#e56b4e]" />
                                        Dados do Imóvel
                                    </CardTitle>
                                    <CardDescription>
                                        Forneça as informações do seu imóvel e expectativas de mercado.
                                    </CardDescription>
                                </CardHeader>
                                <CardContent className="space-y-6">
                                    <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                                        <div>
                                            <Label htmlFor="valor-imovel">Valor do Imóvel (R$)</Label>
                                            <Input
                                                id="valor-imovel"
                                                type="number"
                                                placeholder="Ex: 400000"
                                                value={valorImovel}
                                                onChange={(e) => setValorImovel(e.target.value)}
                                                step="1000"
                                            />
                                        </div>
                                        <div>
                                            <Label htmlFor="area">Área Total (m²)</Label>
                                            <Input
                                                id="area"
                                                type="number"
                                                placeholder="Ex: 80"
                                                value={area}
                                                onChange={(e) => setArea(e.target.value)}
                                            />
                                        </div>
                                        <div>
                                            <Label htmlFor="quartos">Quartos</Label>
                                            <Select value={quartos} onValueChange={setQuartos}>
                                                <SelectTrigger>
                                                    <SelectValue placeholder="Selecione" />
                                                </SelectTrigger>
                                                <SelectContent>
                                                    <SelectItem value="1">1 quarto</SelectItem>
                                                    <SelectItem value="2">2 quartos</SelectItem>
                                                    <SelectItem value="3">3 quartos</SelectItem>
                                                    <SelectItem value="4">4 quartos</SelectItem>
                                                    <SelectItem value="5">5+ quartos</SelectItem>
                                                </SelectContent>
                                            </Select>
                                        </div>
                                        {/* Novo campo para Yield Esperado */}
                                        <div>
                                            <Label htmlFor="yield-esperado">Rendimento (Yield) Anual Esperado (%)</Label>
                                            <Input
                                                id="yield-esperado"
                                                type="number"
                                                placeholder="Ex: 4.5"
                                                value={yieldEsperado}
                                                onChange={(e) => setYieldEsperado(e.target.value)}
                                                step="0.1"
                                            />
                                            <p className="text-xs text-muted-foreground mt-1">
                                                Porcentagem de retorno anual sobre o valor do imóvel.
                                            </p>
                                        </div>
                                        {/* Novo campo para Valor Médio do Aluguel por m² */}
                                        <div className="md:col-span-2"> {/* Ocupa 2 colunas para melhor visualização */}
                                            <Label htmlFor="aluguel-m2-base">Valor Médio do Aluguel por m² na sua Região (R$)</Label>
                                            <Input
                                                id="aluguel-m2-base"
                                                type="number"
                                                placeholder="Ex: 45"
                                                value={aluguelM2Base}
                                                onChange={(e) => setAluguelM2Base(e.target.value)}
                                                step="1"
                                            />
                                            <p className="text-xs text-muted-foreground mt-1">
                                                Pesquise o valor médio do aluguel por m² para imóveis similares na sua rua ou bairro.
                                            </p>
                                        </div>
                                    </div>

                                    <Button
                                        onClick={calcularAluguel}
                                        className="w-full"
                                        disabled={!valorImovel || !area || !quartos || !yieldEsperado || !aluguelM2Base}
                                    >
                                        Calcular Aluguel Sugerido
                                    </Button>

                                    {resultado && (
                                        <div className="mt-6 p-6 bg-[#e56b4e]/10 rounded-lg">
                                            <h3 className="font-semibold mb-4">Valor Sugerido</h3>
                                            <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                                                <div className="text-center">
                                                    <p className="text-sm text-muted-foreground">Mínimo</p>
                                                    <p className="text-lg font-bold text-blue-600">
                                                        R$ {resultado.aluguelMin.toLocaleString('pt-BR', { minimumFractionDigits: 0 })}
                                                    </p>
                                                </div>
                                                <div className="text-center">
                                                    <p className="text-sm text-muted-foreground">Sugerido</p>
                                                    <p className="text-2xl font-bold text-[#e56b4e]">
                                                        R$ {resultado.aluguelSugerido.toLocaleString('pt-BR', { minimumFractionDigits: 0 })}
                                                    </p>
                                                </div>
                                                <div className="text-center">
                                                    <p className="text-sm text-muted-foreground">Máximo</p>
                                                    <p className="text-lg font-bold text-green-600">
                                                        R$ {resultado.aluguelMax.toLocaleString('pt-BR', { minimumFractionDigits: 0 })}
                                                    </p>
                                                </div>
                                            </div>
                                            <div className="mt-4 grid grid-cols-2 gap-4 text-center">
                                                <div>
                                                    <p className="text-sm text-muted-foreground">Yield Anual Calculado</p>
                                                    <p className="text-lg font-semibold text-green-600">
                                                        {resultado.yieldAnual.toFixed(2)}%
                                                    </p>
                                                </div>
                                                <div>
                                                    <p className="text-sm text-muted-foreground">Aluguel por m² Calculado</p>
                                                    <p className="text-lg font-semibold">
                                                        R$ {resultado.aluguelPorM2.toLocaleString('pt-BR', { minimumFractionDigits: 0 })}/m²
                                                    </p>
                                                </div>
                                            </div>
                                            <p className="text-xs text-center text-muted-foreground mt-4">
                                                * Esta é uma estimativa baseada nos dados fornecidos. Para uma precificação precisa, consulte um profissional.
                                            </p>
                                        </div>
                                    )}
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle className="flex items-center gap-2">
                                        <DollarSign className="h-5 w-5 text-[#e56b4e]" />
                                        Fatores que Afetam o Aluguel
                                    </CardTitle>
                                </CardHeader>
                                <CardContent className="space-y-4">
                                    <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                                        <div>
                                            <h3 className="font-semibold mb-3">Aumentam o Valor</h3>
                                            <ul className="space-y-2 text-sm text-muted-foreground">
                                                <li>• Mobiliado/Semi-mobiliado</li>
                                                <li>• Reformado recentemente</li>
                                                <li>• Vaga de garagem</li>
                                                <li>• Área de lazer completa</li>
                                                <li>• Segurança 24h</li>
                                                <li>• Vista privilegiada</li>
                                                <li>• Andar alto</li>
                                            </ul>
                                        </div>
                                        <div>
                                            <h3 className="font-semibold mb-3">Diminuem o Valor</h3>
                                            <ul className="space-y-2 text-sm text-muted-foreground">
                                                <li>• Necessita reformas</li>
                                                <li>• Sem elevador</li>
                                                <li>• Muitos andares a pé</li>
                                                <li>• Problemas estruturais</li>
                                                <li>• Localização menos valorizada</li>
                                                <li>• Falta de iluminação</li>
                                                <li>• Ruído excessivo</li>
                                            </ul>
                                        </div>
                                    </div>
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle className="flex items-center gap-2">
                                        <TrendingUp className="h-5 w-5 text-[#e56b4e]" />
                                        Estratégias de Precificação
                                    </CardTitle>
                                </CardHeader>
                                <CardContent className="space-y-4">
                                    <h3 className="font-semibold">Dicas para definir o aluguel:</h3>
                                    <ul className="space-y-3 text-muted-foreground">
                                        <li>
                                            <span className="font-medium text-foreground">Pesquise a concorrência:</span>
                                            <span className="ml-2">Verifique imóveis similares na região</span>
                                        </li>
                                        <li>
                                            <span className="font-medium text-foreground">Considere a sazonalidade:</span>
                                            <span className="ml-2">Janeiro e fevereiro têm maior demanda</span>
                                        </li>
                                        <li>
                                            <span className="font-medium text-foreground">Flexibilidade inicial:</span>
                                            <span className="ml-2">Prefira alugar logo a ficar vazio</span>
                                        </li>
                                        <li>
                                            <span className="font-medium text-foreground">Reavalie periodicamente:</span>
                                            <span className="ml-2">Acompanhe as mudanças do mercado</span>
                                        </li>
                                    </ul>
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
                                    <Link href="/tools/calculadora-aluguel" className="block text-sm text-[#e56b4e] hover:underline">
                                        → Calculadora de aluguel
                                    </Link>
                                    <Link href="/market/precos-regiao" className="block text-sm text-[#e56b4e] hover:underline">
                                        → Preços por região
                                    </Link>
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle className="text-lg">Entenda o Rendimento (Yield)</CardTitle>
                                </CardHeader>
                                <CardContent className="space-y-3">
                                    <p className="text-sm text-muted-foreground">
                                        O Yield (rendimento) anual é a relação entre a receita anual de aluguel e o valor do imóvel.
                                        É um indicador importante da rentabilidade do investimento imobiliário.
                                        Pesquise os yields médios para imóveis similares na sua região para ter uma base.
                                    </p>
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle className="text-lg">Consultoria Especializada</CardTitle>
                                </CardHeader>
                                <CardContent>
                                    <p className="text-sm text-muted-foreground mb-4">
                                        Precisa de ajuda profissional para definir o valor?
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

export default PricingGuidePage;
