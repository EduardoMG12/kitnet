'use client';

import React, { useState } from "react";

import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { ArrowLeft, Calculator, MapPin, Home } from "lucide-react";
import MainLayout from "@/components/layout/MainLayout";
import Link from "next/link";

const PropertyValuePage = () => {
    const [area, setArea] = useState("");
    const [quartos, setQuartos] = useState("");
    const [banheiros, setBanheiros] = useState("");
    const [vagas, setVagas] = useState("");
    // Novo estado para o valor médio do m² fornecido pelo usuário
    const [valorM2Base, setValorM2Base] = useState("");
    const [idade, setIdade] = useState("");
    const [resultado, setResultado] = useState<{
        valorEstimado: number;
        valorMin: number;
        valorMax: number;
        valorPorM2Calculado: number; // Renomeado para evitar confusão com o valor base
    } | null>(null);

    const calcularValor = () => {
        const areaNum = Number.parseFloat(area);
        const valorM2BaseNum = Number.parseFloat(valorM2Base);

        // Validação básica: área, quartos e valor base do m² são obrigatórios
        if (!areaNum || !quartos || !valorM2BaseNum) {
            setResultado(null); // Limpa o resultado se a validação falhar
            return;
        }

        // O valor base agora vem diretamente do input do usuário
        const valorBaseInicial = valorM2BaseNum;

        // Ajustes por características (mantidos da sua lógica original)
        // Certifique-se de que os valores convertidos sejam números válidos
        const numQuartos = Number.parseInt(quartos) || 0;
        const numBanheiros = Number.parseInt(banheiros) || 0;
        const numVagas = Number.parseInt(vagas || "0") || 0;
        const numIdade = Number.parseInt(idade || "0") || 0;

        const fatorQuartos = 1 + (numQuartos - 2) * 0.1; // Ex: 2 quartos = 1; 3 quartos = 1.1; 1 quarto = 0.9
        const fatorBanheiros = 1 + (numBanheiros - 1) * 0.05; // Ex: 1 banheiro = 1; 2 banheiros = 1.05
        const fatorVagas = 1 + numVagas * 0.08; // Ex: 1 vaga = 1.08; 0 vagas = 1
        const fatorIdade = Math.max(0.5, 1 - (numIdade / 100)); // Depreciação por idade, com mínimo de 50% do valor

        // Cálculo do valor estimado
        const valorEstimado = areaNum * valorBaseInicial * fatorQuartos * fatorBanheiros * fatorVagas * fatorIdade;
        const valorMin = valorEstimado * 0.85; // Margem de 15% para baixo
        const valorMax = valorEstimado * 1.15; // Margem de 15% para cima

        setResultado({
            valorEstimado: valorEstimado,
            valorMin: valorMin,
            valorMax: valorMax,
            valorPorM2Calculado: valorEstimado / areaNum // O valor por m² final após os ajustes
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
                        <h1 className="text-4xl font-heading font-bold mb-4">Quanto Vale Meu Imóvel?</h1>
                        <p className="text-xl text-muted-foreground">
                            Avaliação estimada do valor do seu imóvel
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
                                        Forneça as informações do seu imóvel para uma estimativa.
                                    </CardDescription>
                                </CardHeader>
                                <CardContent className="space-y-6">
                                    <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                                        <div>
                                            <Label htmlFor="valorM2Base">Valor Médio do m² na sua Região (R$)</Label>
                                            <Input
                                                id="valorM2Base"
                                                type="number"
                                                placeholder="Ex: 8000"
                                                value={valorM2Base}
                                                onChange={(e) => setValorM2Base(e.target.value)}
                                                step="100" // Sugere incrementos de 100
                                            />
                                            <p className="text-xs text-muted-foreground mt-1">
                                                Pesquise o valor médio do m² para imóveis similares na sua rua ou bairro.
                                            </p>
                                        </div>
                                        <div>
                                            <Label htmlFor="area">Área Total (m²)</Label>
                                            <Input
                                                id="area"
                                                type="number"
                                                placeholder="80"
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
                                        <div>
                                            <Label htmlFor="banheiros">Banheiros</Label>
                                            <Select value={banheiros} onValueChange={setBanheiros}>
                                                <SelectTrigger>
                                                    <SelectValue placeholder="Selecione" />
                                                </SelectTrigger>
                                                <SelectContent>
                                                    <SelectItem value="1">1 banheiro</SelectItem>
                                                    <SelectItem value="2">2 banheiros</SelectItem>
                                                    <SelectItem value="3">3 banheiros</SelectItem>
                                                    <SelectItem value="4">4+ banheiros</SelectItem>
                                                </SelectContent>
                                            </Select>
                                        </div>
                                        <div>
                                            <Label htmlFor="vagas">Vagas de Garagem</Label>
                                            <Select value={vagas} onValueChange={setVagas}>
                                                <SelectTrigger>
                                                    <SelectValue placeholder="Selecione" />
                                                </SelectTrigger>
                                                <SelectContent>
                                                    <SelectItem value="0">Sem vaga</SelectItem>
                                                    <SelectItem value="1">1 vaga</SelectItem>
                                                    <SelectItem value="2">2 vagas</SelectItem>
                                                    <SelectItem value="3">3+ vagas</SelectItem>
                                                </SelectContent>
                                            </Select>
                                        </div>
                                        <div>
                                            <Label htmlFor="idade">Idade do Imóvel (anos)</Label>
                                            <Input
                                                id="idade"
                                                type="number"
                                                placeholder="10"
                                                value={idade}
                                                onChange={(e) => setIdade(e.target.value)}
                                            />
                                        </div>
                                    </div>

                                    <Button
                                        onClick={calcularValor}
                                        className="w-full"
                                        disabled={!area || !quartos || !valorM2Base} // Validação atualizada
                                    >
                                        Calcular Valor
                                    </Button>

                                    {resultado && (
                                        <div className="mt-6 p-6 bg-[#e56b4e]/10 rounded-lg">
                                            <h3 className="font-semibold mb-4">Avaliação Estimada</h3>
                                            <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                                                <div className="text-center">
                                                    <p className="text-sm text-muted-foreground">Valor Mínimo</p>
                                                    <p className="text-lg font-bold text-blue-600">
                                                        R$ {resultado.valorMin.toLocaleString('pt-BR', { minimumFractionDigits: 0 })}
                                                    </p>
                                                </div>
                                                <div className="text-center">
                                                    <p className="text-sm text-muted-foreground">Valor Estimado</p>
                                                    <p className="text-2xl font-bold text-[#e56b4e]">
                                                        R$ {resultado.valorEstimado.toLocaleString('pt-BR', { minimumFractionDigits: 0 })}
                                                    </p>
                                                </div>
                                                <div className="text-center">
                                                    <p className="text-sm text-muted-foreground">Valor Máximo</p>
                                                    <p className="text-lg font-bold text-green-600">
                                                        R$ {resultado.valorMax.toLocaleString('pt-BR', { minimumFractionDigits: 0 })}
                                                    </p>
                                                </div>
                                            </div>
                                            <div className="mt-4 text-center">
                                                <p className="text-sm text-muted-foreground">Valor por m² Calculado</p>
                                                <p className="text-lg font-semibold">
                                                    R$ {resultado.valorPorM2Calculado.toLocaleString('pt-BR', { minimumFractionDigits: 0 })}/m²
                                                </p>
                                            </div>
                                            <p className="text-xs text-center text-muted-foreground mt-4">
                                                * Esta é uma estimativa baseada nos dados fornecidos. Para uma avaliação precisa, consulte um profissional.
                                            </p>
                                        </div>
                                    )}
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle className="flex items-center gap-2">
                                        <MapPin className="h-5 w-5 text-[#e56b4e]" />
                                        Fatores que Influenciam o Valor
                                    </CardTitle>
                                </CardHeader>
                                <CardContent className="space-y-4">
                                    <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                                        <div>
                                            <h3 className="font-semibold mb-3">Fatores Positivos</h3>
                                            <ul className="space-y-2 text-sm text-muted-foreground">
                                                <li>• Localização privilegiada</li>
                                                <li>• Boa infraestrutura</li>
                                                <li>• Transporte público</li>
                                                <li>• Segurança da região</li>
                                                <li>• Comércio próximo</li>
                                                <li>• Imóvel reformado</li>
                                                <li>• Vista privilegiada</li>
                                            </ul>
                                        </div>
                                        <div>
                                            <h3 className="font-semibold mb-3">Fatores Negativos</h3>
                                            <ul className="space-y-2 text-sm text-muted-foreground">
                                                <li>• Necessidade de reformas</li>
                                                <li>• Problemas estruturais</li>
                                                <li>• Localização menos valorizada</li>
                                                <li>• Falta de documentação</li>
                                                <li>• Ruído excessivo</li>
                                                <li>• Ausência de elevador</li>
                                                <li>• Problemas de umidade</li>
                                            </ul>
                                        </div>
                                    </div>
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle>Próximos Passos</CardTitle>
                                </CardHeader>
                                <CardContent>
                                    <p className="text-muted-foreground mb-4">
                                        Esta é uma estimativa inicial. Para uma avaliação mais precisa, considere:
                                    </p>
                                    <ul className="space-y-2 text-muted-foreground">
                                        <li>• Avaliação presencial por corretor</li>
                                        <li>• Pesquisa de imóveis similares na região</li>
                                        <li>• Análise de tendências do mercado</li>
                                        <li>• Laudo de avaliação técnica</li>
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
                                    <Link href="/tools/quanto-cobrar" className="block text-sm text-[#e56b4e] hover:underline">
                                        → Quanto cobrar de aluguel
                                    </Link>
                                    <Link href="/tools/comprar-ou-alugar" className="block text-sm text-[#e56b4e] hover:underline">
                                        → Comprar ou alugar?
                                    </Link>
                                    <Link href="/market/precos-regiao" className="block text-sm text-[#e56b4e] hover:underline">
                                        → Preços por região
                                    </Link>
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle className="text-lg">Avaliação Profissional</CardTitle>
                                </CardHeader>
                                <CardContent>
                                    <p className="text-sm text-muted-foreground mb-4">
                                        Precisa de uma avaliação mais detalhada? Nossos corretores podem ajudar.
                                    </p>
                                    <Button className="w-full">
                                        Solicitar Avaliação
                                    </Button>
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle className="text-lg">Vender seu Imóvel</CardTitle>
                                </CardHeader>
                                <CardContent>
                                    <p className="text-sm text-muted-foreground mb-4">
                                        Quer vender ou alugar? Cadastre seu imóvel conosco.
                                    </p>
                                    <Button variant="outline" className="w-full">
                                        Anunciar Imóvel
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

export default PropertyValuePage;
