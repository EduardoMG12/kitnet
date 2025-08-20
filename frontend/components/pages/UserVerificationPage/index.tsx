'use client';

import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Checkbox } from "@/components/ui/checkbox";
import { Textarea } from "@/components/ui/textarea";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import MainLayout from "@/components/layout/MainLayout";
import { ArrowLeft, Upload, Shield, User, FileText, CreditCard } from "lucide-react";
import { useToast } from "@/hooks/use-toast";
import Link from "next/link";

const UserVerificationPage = () => {
    const { toast } = useToast();
    const [lgpdConsent, setLgpdConsent] = useState(false);
    const [marketingConsent, setMarketingConsent] = useState(false);
    const [formData, setFormData] = useState({
        cpf: "",
        rg: "",
        cnh: "",
        profession: "",
        monthlyIncome: "",
        companyName: "",
        workTime: "",
        emergencyContact: "",
        emergencyPhone: "",
    });

    const handleInputChange = (field: string, value: string) => {
        setFormData(prev => ({ ...prev, [field]: value }));
    };

    const handleFileUpload = (type: string) => {
        toast({
            title: "Arquivo enviado",
            description: `${type} foi enviado com sucesso e será analisado em até 24h.`,
        });
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        if (!lgpdConsent) {
            toast({
                title: "Consentimento necessário",
                description: "Você precisa concordar com os termos da LGPD para continuar.",
                variant: "destructive",
            });
            return;
        }

        toast({
            title: "Dados enviados!",
            description: "Seus dados foram enviados para verificação. Em breve você receberá o resultado da análise.",
        });
    };

    const formatCPF = (value: string) => {
        return value
            .replace(/\D/g, "")
            .replace(/(\d{3})(\d)/, "$1.$2")
            .replace(/(\d{3})(\d)/, "$1.$2")
            .replace(/(\d{3})(\d{1,2})/, "$1-$2")
            .replace(/(-\d{2})\d+?$/, "$1");
    };

    const formatRG = (value: string) => {
        return value
            .replace(/\D/g, "")
            .replace(/(\d{2})(\d)/, "$1.$2")
            .replace(/(\d{3})(\d)/, "$1.$2")
            .replace(/(\d{3})(\d{1,1})/, "$1-$2")
            .replace(/(-\d{1})\d+?$/, "$1");
    };

    return (
        <MainLayout>
            <div className="min-h-screen bg-cream py-8">
                <div className="container mx-auto px-4 max-w-4xl">
                    <div className="mb-6">
                        <Link href="/properties" className="inline-flex items-center text-terracotta hover:underline">
                            <ArrowLeft className="h-4 w-4 mr-2" />
                            Voltar para imóveis
                        </Link>
                    </div>

                    <Card className="mb-8">
                        <CardHeader className="text-center">
                            <CardTitle className="text-3xl font-heading text-deepblue flex items-center justify-center gap-2">
                                <Shield className="h-8 w-8 text-terracotta" />
                                Verificação de Dados
                            </CardTitle>
                            <CardDescription className="text-lg">
                                Complete seu perfil para aumentar suas chances de aprovação
                            </CardDescription>
                        </CardHeader>
                    </Card>

                    <form onSubmit={handleSubmit}>
                        <Tabs defaultValue="personal" className="space-y-6">
                            <TabsList className="grid w-full grid-cols-4">
                                <TabsTrigger value="personal" className="flex items-center gap-2">
                                    <User className="h-4 w-4" />
                                    Pessoais
                                </TabsTrigger>
                                <TabsTrigger value="financial" className="flex items-center gap-2">
                                    <CreditCard className="h-4 w-4" />
                                    Financeiros
                                </TabsTrigger>
                                <TabsTrigger value="documents" className="flex items-center gap-2">
                                    <FileText className="h-4 w-4" />
                                    Documentos
                                </TabsTrigger>
                                <TabsTrigger value="consent" className="flex items-center gap-2">
                                    <Shield className="h-4 w-4" />
                                    Consentimento
                                </TabsTrigger>
                            </TabsList>

                            <TabsContent value="personal">
                                <Card>
                                    <CardHeader>
                                        <CardTitle>Dados Pessoais</CardTitle>
                                        <CardDescription>
                                            Informações básicas para verificação de identidade
                                        </CardDescription>
                                    </CardHeader>
                                    <CardContent className="space-y-4">
                                        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                                            <div>
                                                <Label htmlFor="cpf">CPF *</Label>
                                                <Input
                                                    id="cpf"
                                                    value={formData.cpf}
                                                    onChange={(e) => handleInputChange("cpf", formatCPF(e.target.value))}
                                                    placeholder="000.000.000-00"
                                                    maxLength={14}
                                                    required
                                                />
                                            </div>
                                            <div>
                                                <Label htmlFor="rg">RG *</Label>
                                                <Input
                                                    id="rg"
                                                    value={formData.rg}
                                                    onChange={(e) => handleInputChange("rg", formatRG(e.target.value))}
                                                    placeholder="00.000.000-0"
                                                    maxLength={12}
                                                    required
                                                />
                                            </div>
                                            <div>
                                                <Label htmlFor="cnh">CNH (opcional)</Label>
                                                <Input
                                                    id="cnh"
                                                    value={formData.cnh}
                                                    onChange={(e) => handleInputChange("cnh", e.target.value)}
                                                    placeholder="Número da CNH"
                                                />
                                            </div>
                                            <div>
                                                <Label htmlFor="profession">Profissão *</Label>
                                                <Input
                                                    id="profession"
                                                    value={formData.profession}
                                                    onChange={(e) => handleInputChange("profession", e.target.value)}
                                                    placeholder="Sua profissão"
                                                    required
                                                />
                                            </div>
                                        </div>
                                        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                                            <div>
                                                <Label htmlFor="emergencyContact">Contato de Emergência *</Label>
                                                <Input
                                                    id="emergencyContact"
                                                    value={formData.emergencyContact}
                                                    onChange={(e) => handleInputChange("emergencyContact", e.target.value)}
                                                    placeholder="Nome completo"
                                                    required
                                                />
                                            </div>
                                            <div>
                                                <Label htmlFor="emergencyPhone">Telefone de Emergência *</Label>
                                                <Input
                                                    id="emergencyPhone"
                                                    value={formData.emergencyPhone}
                                                    onChange={(e) => handleInputChange("emergencyPhone", e.target.value)}
                                                    placeholder="(11) 99999-9999"
                                                    required
                                                />
                                            </div>
                                        </div>
                                    </CardContent>
                                </Card>
                            </TabsContent>

                            <TabsContent value="financial">
                                <Card>
                                    <CardHeader>
                                        <CardTitle>Informações Financeiras</CardTitle>
                                        <CardDescription>
                                            Dados para análise de capacidade de pagamento
                                        </CardDescription>
                                    </CardHeader>
                                    <CardContent className="space-y-4">
                                        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                                            <div>
                                                <Label htmlFor="monthlyIncome">Renda Mensal Bruta *</Label>
                                                <Input
                                                    id="monthlyIncome"
                                                    value={formData.monthlyIncome}
                                                    onChange={(e) => handleInputChange("monthlyIncome", e.target.value)}
                                                    placeholder="R$ 0,00"
                                                    required
                                                />
                                            </div>
                                            <div>
                                                <Label htmlFor="companyName">Empresa *</Label>
                                                <Input
                                                    id="companyName"
                                                    value={formData.companyName}
                                                    onChange={(e) => handleInputChange("companyName", e.target.value)}
                                                    placeholder="Nome da empresa"
                                                    required
                                                />
                                            </div>
                                            <div className="md:col-span-2">
                                                <Label htmlFor="workTime">Tempo de Trabalho na Empresa *</Label>
                                                <Input
                                                    id="workTime"
                                                    value={formData.workTime}
                                                    onChange={(e) => handleInputChange("workTime", e.target.value)}
                                                    placeholder="Ex: 2 anos e 3 meses"
                                                    required
                                                />
                                            </div>
                                        </div>
                                    </CardContent>
                                </Card>
                            </TabsContent>

                            <TabsContent value="documents">
                                <Card>
                                    <CardHeader>
                                        <CardTitle>Upload de Documentos</CardTitle>
                                        <CardDescription>
                                            Envie os documentos necessários para verificação
                                        </CardDescription>
                                    </CardHeader>
                                    <CardContent className="space-y-6">
                                        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                                            <div className="space-y-2">
                                                <Label>Foto do RG (frente e verso) *</Label>
                                                <div className="border-2 border-dashed border-gray-300 rounded-lg p-6 text-center hover:border-terracotta transition-colors">
                                                    <Upload className="h-8 w-8 mx-auto mb-2 text-gray-400" />
                                                    <p className="text-sm text-gray-600 mb-2">Clique para enviar ou arraste aqui</p>
                                                    <Button
                                                        type="button"
                                                        variant="outline"
                                                        onClick={() => handleFileUpload("RG")}
                                                    >
                                                        Enviar RG
                                                    </Button>
                                                </div>
                                            </div>

                                            <div className="space-y-2">
                                                <Label>Foto do CPF *</Label>
                                                <div className="border-2 border-dashed border-gray-300 rounded-lg p-6 text-center hover:border-terracotta transition-colors">
                                                    <Upload className="h-8 w-8 mx-auto mb-2 text-gray-400" />
                                                    <p className="text-sm text-gray-600 mb-2">Clique para enviar ou arraste aqui</p>
                                                    <Button
                                                        type="button"
                                                        variant="outline"
                                                        onClick={() => handleFileUpload("CPF")}
                                                    >
                                                        Enviar CPF
                                                    </Button>
                                                </div>
                                            </div>

                                            <div className="space-y-2">
                                                <Label>Comprovante de Renda *</Label>
                                                <div className="border-2 border-dashed border-gray-300 rounded-lg p-6 text-center hover:border-terracotta transition-colors">
                                                    <Upload className="h-8 w-8 mx-auto mb-2 text-gray-400" />
                                                    <p className="text-sm text-gray-600 mb-2">Holerite, declaração de IR, etc.</p>
                                                    <Button
                                                        type="button"
                                                        variant="outline"
                                                        onClick={() => handleFileUpload("Comprovante de Renda")}
                                                    >
                                                        Enviar Comprovante
                                                    </Button>
                                                </div>
                                            </div>

                                            <div className="space-y-2">
                                                <Label>Comprovante de Residência *</Label>
                                                <div className="border-2 border-dashed border-gray-300 rounded-lg p-6 text-center hover:border-terracotta transition-colors">
                                                    <Upload className="h-8 w-8 mx-auto mb-2 text-gray-400" />
                                                    <p className="text-sm text-gray-600 mb-2">Conta de luz, água, telefone, etc.</p>
                                                    <Button
                                                        type="button"
                                                        variant="outline"
                                                        onClick={() => handleFileUpload("Comprovante de Residência")}
                                                    >
                                                        Enviar Comprovante
                                                    </Button>
                                                </div>
                                            </div>

                                            <div className="space-y-2">
                                                <Label>Foto (selfie) *</Label>
                                                <div className="border-2 border-dashed border-gray-300 rounded-lg p-6 text-center hover:border-terracotta transition-colors">
                                                    <Upload className="h-8 w-8 mx-auto mb-2 text-gray-400" />
                                                    <p className="text-sm text-gray-600 mb-2">Foto atual para verificação</p>
                                                    <Button
                                                        type="button"
                                                        variant="outline"
                                                        onClick={() => handleFileUpload("Foto")}
                                                    >
                                                        Enviar Foto
                                                    </Button>
                                                </div>
                                            </div>

                                            <div className="space-y-2">
                                                <Label>CNH (se informada)</Label>
                                                <div className="border-2 border-dashed border-gray-300 rounded-lg p-6 text-center hover:border-terracotta transition-colors">
                                                    <Upload className="h-8 w-8 mx-auto mb-2 text-gray-400" />
                                                    <p className="text-sm text-gray-600 mb-2">Frente e verso da CNH</p>
                                                    <Button
                                                        type="button"
                                                        variant="outline"
                                                        onClick={() => handleFileUpload("CNH")}
                                                    >
                                                        Enviar CNH
                                                    </Button>
                                                </div>
                                            </div>
                                        </div>
                                    </CardContent>
                                </Card>
                            </TabsContent>

                            <TabsContent value="consent">
                                <Card>
                                    <CardHeader>
                                        <CardTitle>Consentimento LGPD</CardTitle>
                                        <CardDescription>
                                            Seus dados estão protegidos pela Lei Geral de Proteção de Dados
                                        </CardDescription>
                                    </CardHeader>
                                    <CardContent className="space-y-6">
                                        <div className="bg-blue-50 p-4 rounded-lg">
                                            <h3 className="font-semibold mb-2">Como seus dados serão utilizados:</h3>
                                            <ul className="text-sm space-y-1 text-gray-700">
                                                <li>• Verificação de identidade e capacidade financeira</li>
                                                <li>• Análise de score de crédito através de bureaus de crédito</li>
                                                <li>• Comunicação sobre propostas de locação</li>
                                                <li>• Cumprimento de obrigações legais</li>
                                                <li>• Você pode solicitar exclusão dos dados a qualquer momento</li>
                                            </ul>
                                        </div>

                                        <div className="space-y-4">
                                            <div className="flex items-start space-x-3">
                                                <Checkbox
                                                    id="lgpd-consent"
                                                    checked={lgpdConsent}
                                                    onCheckedChange={(checked) => setLgpdConsent(checked === true)}
                                                />
                                                <div className="space-y-1 leading-none">
                                                    <Label htmlFor="lgpd-consent" className="text-sm font-medium">
                                                        Concordo com o tratamento dos meus dados pessoais *
                                                    </Label>
                                                    <p className="text-xs text-muted-foreground">
                                                        Autorizo o uso dos meus dados para verificação de capacidade locatícia e comunicação sobre propostas.
                                                        <Link href="/privacy" className="text-terracotta hover:underline ml-1">
                                                            Leia nossa Política de Privacidade
                                                        </Link>
                                                    </p>
                                                </div>
                                            </div>

                                            <div className="flex items-start space-x-3">
                                                <Checkbox
                                                    id="marketing-consent"
                                                    checked={marketingConsent}
                                                    onCheckedChange={(checked) => setMarketingConsent(checked === true)}
                                                />
                                                <div className="space-y-1 leading-none">
                                                    <Label htmlFor="marketing-consent" className="text-sm font-medium">
                                                        Aceito receber comunicações de marketing (opcional)
                                                    </Label>
                                                    <p className="text-xs text-muted-foreground">
                                                        Novos imóveis, promoções e conteúdo relevante por e-mail e WhatsApp.
                                                    </p>
                                                </div>
                                            </div>
                                        </div>

                                        <div className="border-t pt-4">
                                            <p className="text-xs text-gray-600">
                                                <strong>Seus direitos:</strong> Você pode solicitar acesso, correção, exclusão ou portabilidade dos seus dados a qualquer momento através do nosso canal de atendimento.
                                            </p>
                                        </div>
                                    </CardContent>
                                </Card>
                            </TabsContent>
                        </Tabs>

                        <div className="mt-8 text-center">
                            <Button
                                type="submit"
                                className="bg-terracotta hover:bg-terracotta/90 px-8 py-3"
                                disabled={!lgpdConsent}
                            >
                                Enviar Dados para Verificação
                            </Button>
                            <p className="text-sm text-gray-600 mt-2">
                                * Campos obrigatórios
                            </p>
                        </div>
                    </form>
                </div>
            </div>
        </MainLayout>
    );
};

export default UserVerificationPage;