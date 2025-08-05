'use client';
import React, { useState } from "react";

import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { ArrowLeft, AlertTriangle, Calculator } from "lucide-react";
import MainLayout from "@/components/layout/MainLayout";
import { Breadcrumb, BreadcrumbItem, BreadcrumbLink, BreadcrumbList, BreadcrumbPage, BreadcrumbSeparator } from "@/components/ui/breadcrumb";
import Link from "next/link";

const BrokenContractPage = () => {
    const [showCalculator, setShowCalculator] = useState(false);
    const [valorAluguel, setValorAluguel] = useState("");
    const [tempoContrato, setTempoContrato] = useState("");
    const [mesesDecorridos, setMesesDecorridos] = useState("");
    const [tipoQuebra, setTipoQuebra] = useState("");
    const [multa, setMulta] = useState<number | null>(null);

    const calcularMulta = () => {
        const aluguel = Number.parseFloat(valorAluguel);
        const tempoTotal = Number.parseInt(tempoContrato);
        const mesesPassados = Number.parseInt(mesesDecorridos);

        // Verifica se todos os campos estão preenchidos
        if (!aluguel || !tempoTotal || !mesesPassados || !tipoQuebra) return;

        // Se os meses passados forem maiores ou iguais ao tempo total, não há multa.
        if (mesesPassados >= tempoTotal) {
            setMulta(0);
            return;
        }

        let valorMulta = 0;

        // A multa base comum é de 3 aluguéis
        const multaBase = 3 * aluguel;

        if (tipoQuebra === "inquilino") {
            // Regra comum de contrato de 30 meses: após 12 meses, pode sair sem multa, com aviso prévio de 30 dias.
            // Se o contrato for menor, a multa proporcional é aplicada.
            if (tempoTotal >= 30 && mesesPassados >= 12) {
                valorMulta = 0; // Saída sem multa, com aviso prévio.
            } else {
                // Multa proporcional ao tempo restante do contrato.
                const mesesRestantes = tempoTotal - mesesPassados;
                valorMulta = (multaBase / tempoTotal) * mesesRestantes;
            }
        } else if (tipoQuebra === "proprietario") {
            // A multa do proprietário também é proporcional, com base na mesma multa total.
            // É aplicada se o proprietário pedir o imóvel de volta sem um motivo justificado.
            const mesesRestantes = tempoTotal - mesesPassados;
            valorMulta = (multaBase / tempoTotal) * mesesRestantes;
        }

        setMulta(valorMulta);
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
                            <BreadcrumbPage>Quebra de Contrato</BreadcrumbPage>
                        </BreadcrumbList>
                    </Breadcrumb>

                    <div className="max-w-4xl mx-auto">
                        <header className="mb-8">
                            <h1 className="text-3xl md:text-4xl font-heading font-bold mb-4">
                                Quebra de Contrato de Aluguel
                            </h1>
                            <p className="text-lg text-muted-foreground">
                                Entenda seus direitos e deveres na rescisão antecipada do contrato
                            </p>
                        </header>

                        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
                            <div className="lg:col-span-2 space-y-8">
                                <Card>
                                    <CardHeader>
                                        <CardTitle className="flex items-center gap-2">
                                            <AlertTriangle className="h-5 w-5 text-amber-600" />
                                            Multa por Rescisão
                                        </CardTitle>
                                    </CardHeader>
                                    <CardContent className="space-y-4">
                                        <div>
                                            <h3 className="font-medium mb-2">Inquilino (Locatário)</h3>
                                            <ul className="text-sm text-muted-foreground space-y-1 ml-4">
                                                <li>• Multa proporcional ao tempo restante do contrato</li>
                                                <li>• Em contratos de 30 meses, pode sair sem multa após 12 meses (com aviso de 30 dias)</li>
                                                <li>• Não há multa em caso de transferência de trabalho</li>
                                            </ul>
                                        </div>

                                        <div>
                                            <h3 className="font-medium mb-2">Proprietário (Locador)</h3>
                                            <ul className="text-sm text-muted-foreground space-y-1 ml-4">
                                                <li>• Deve respeitar prazo de 30 dias de aviso</li>
                                                <li>• Multa proporcional ao tempo restante do contrato</li>
                                                <li>• Apenas em casos específicos previstos em lei</li>
                                            </ul>
                                        </div>
                                    </CardContent>
                                </Card>

                                <Card>
                                    <CardHeader>
                                        <CardTitle>Motivos Justificados</CardTitle>
                                    </CardHeader>
                                    <CardContent className="space-y-4">
                                        <div>
                                            <h3 className="font-medium mb-2">Sem Multa para o Inquilino</h3>
                                            <ul className="text-sm text-muted-foreground space-y-1 ml-4">
                                                <li>• Transferência comprovada de trabalho</li>
                                                <li>• Aposentadoria ou demissão sem justa causa</li>
                                                <li>• Problemas de saúde grave</li>
                                            </ul>
                                        </div>

                                        <div>
                                            <h3 className="font-medium mb-2">Sem Multa para o Proprietário</h3>
                                            <ul className="text-sm text-muted-foreground space-y-1 ml-4">
                                                <li>• Descumprimento de cláusulas contratuais</li>
                                                <li>• Atraso superior a 3 meses</li>
                                                <li>• Danos ao imóvel</li>
                                            </ul>
                                        </div>
                                    </CardContent>
                                </Card>
                            </div>

                            <div className="space-y-6">
                                <Card>
                                    <CardHeader>
                                        <CardTitle className="text-lg">Calculadora de Multa</CardTitle>
                                    </CardHeader>
                                    <CardContent>
                                        <p className="text-sm text-muted-foreground mb-4">
                                            Calcule o valor da multa de rescisão do seu contrato
                                        </p>
                                        <Button
                                            onClick={() => setShowCalculator(!showCalculator)}
                                            className="w-full bg-[#e56b4e] hover:bg-[#e56b4e]/90"
                                        >
                                            <Calculator className="mr-2 h-4 w-4" />
                                            {showCalculator ? "Ocultar" : "Calcular Multa"}
                                        </Button>
                                    </CardContent>
                                </Card>

                                {showCalculator && (
                                    <Card>
                                        <CardHeader>
                                            <CardTitle className="text-lg">Calculadora de Multa</CardTitle>
                                        </CardHeader>
                                        <CardContent className="space-y-4">
                                            <div>
                                                <Label htmlFor="tipoQuebra">Quem está rescindindo?</Label>
                                                <Select value={tipoQuebra} onValueChange={setTipoQuebra}>
                                                    <SelectTrigger>
                                                        <SelectValue placeholder="Selecione..." />
                                                    </SelectTrigger>
                                                    <SelectContent>
                                                        <SelectItem value="inquilino">Inquilino</SelectItem>
                                                        <SelectItem value="proprietario">Proprietário</SelectItem>
                                                    </SelectContent>
                                                </Select>
                                            </div>

                                            <div>
                                                <Label htmlFor="valorAluguel">Valor do Aluguel (R$)</Label>
                                                <Input
                                                    id="valorAluguel"
                                                    type="number"
                                                    placeholder="Ex: 1500"
                                                    value={valorAluguel}
                                                    onChange={(e) => setValorAluguel(e.target.value)}
                                                />
                                            </div>

                                            <div>
                                                <Label htmlFor="tempoContrato">Tempo total do contrato (meses)</Label>
                                                <Input
                                                    id="tempoContrato"
                                                    type="number"
                                                    placeholder="Ex: 24"
                                                    value={tempoContrato}
                                                    onChange={(e) => setTempoContrato(e.target.value)}
                                                />
                                            </div>

                                            <div>
                                                <Label htmlFor="mesesDecorridos">Meses já decorridos</Label>
                                                <Input
                                                    id="mesesDecorridos"
                                                    type="number"
                                                    placeholder="Ex: 8"
                                                    value={mesesDecorridos}
                                                    onChange={(e) => setMesesDecorridos(e.target.value)}
                                                />
                                            </div>

                                            <Button
                                                onClick={calcularMulta}
                                                className="w-full"
                                                disabled={!valorAluguel || !tempoContrato || !mesesDecorridos || !tipoQuebra}
                                            >
                                                Calcular Multa
                                            </Button>

                                            {multa !== null && (
                                                <div className="p-4 bg-muted rounded-lg">
                                                    <h3 className="font-medium mb-2">Resultado:</h3>
                                                    <p className="text-lg font-bold text-[#e56b4e]">
                                                        Multa: R$ {multa.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}
                                                    </p>
                                                    {tipoQuebra === "inquilino" && Number.parseInt(mesesDecorridos) < Number.parseInt(tempoContrato) && (
                                                        <p className="text-sm text-muted-foreground mt-2">
                                                            * A multa é proporcional ao tempo restante do contrato.
                                                        </p>
                                                    )}
                                                </div>
                                            )}
                                        </CardContent>
                                    </Card>
                                )}

                                <Card>
                                    <CardHeader>
                                        <CardTitle className="text-lg">Artigos Relacionados</CardTitle>
                                    </CardHeader>
                                    <CardContent className="space-y-3">
                                        <Link href="/guides/contrato-aluguel" className="block text-sm text-[#e56b4e] hover:underline">
                                            → Contrato de Aluguel
                                        </Link>
                                        <Link href="/guides/lei-inquilinato" className="block text-sm text-[#e56b4e] hover:underline">
                                            → Lei do Inquilinato
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

export default BrokenContractPage;