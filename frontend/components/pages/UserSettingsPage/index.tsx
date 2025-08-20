'use client';

import React, { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Separator } from "@/components/ui/separator";
import { Switch } from "@/components/ui/switch";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Textarea } from "@/components/ui/textarea";
import { Badge } from "@/components/ui/badge";
import { Camera, Save, User, Bell, Shield, CreditCard, MapPin, Phone, Mail } from "lucide-react";

const UserSettingsPage = () => {
    const [profileData, setProfileData] = useState({
        name: "João Silva",
        email: "joao.silva@email.com",
        phone: "(11) 99999-9999",
        bio: "Procurando por um apartamento em São Paulo, preferencialmente próximo ao metrô.",
        location: "São Paulo, SP"
    });

    const [preferences, setPreferences] = useState({
        emailNotifications: true,
        smsNotifications: false,
        marketingEmails: true,
        newPropertyAlerts: true
    });

    const handleProfileUpdate = (field: string, value: string) => {
        setProfileData(prev => ({
            ...prev,
            [field]: value
        }));
    };

    const handlePreferenceToggle = (preference: string) => {
        setPreferences(prev => ({
            ...prev,
            [preference]: !prev[preference as keyof typeof prev]
        }));
    };

    return (
        <div className="min-h-screen flex flex-col">
            <div className="flex-1 container px-4 mx-auto max-w-7xl py-8">
                <div className="max-w-4xl mx-auto">
                    <div className="mb-8">
                        <h1 className="font-heading text-3xl font-bold mb-2">Configurações da Conta</h1>
                        <p className="text-muted-foreground">
                            Gerencie suas informações pessoais e preferências
                        </p>
                    </div>

                    <Tabs defaultValue="profile" className="space-y-6">
                        <TabsList className="grid w-full grid-cols-4">
                            <TabsTrigger value="profile">Perfil</TabsTrigger>
                            <TabsTrigger value="notifications">Notificações</TabsTrigger>
                            <TabsTrigger value="security">Segurança</TabsTrigger>
                            <TabsTrigger value="billing">Pagamento</TabsTrigger>
                        </TabsList>

                        <TabsContent value="profile" className="space-y-6">
                            <Card>
                                <CardHeader>
                                    <CardTitle className="flex items-center gap-2">
                                        <User className="h-5 w-5" />
                                        Informações Pessoais
                                    </CardTitle>
                                    <CardDescription>
                                        Atualize suas informações pessoais e de contato
                                    </CardDescription>
                                </CardHeader>
                                <CardContent className="space-y-6">
                                    <div className="flex items-center gap-6">
                                        <Avatar className="h-24 w-24">
                                            <AvatarImage src="/placeholder.svg" alt={profileData.name} />
                                            <AvatarFallback className="bg-terracotta text-white text-lg">
                                                {profileData.name.split(' ').map(n => n[0]).join('')}
                                            </AvatarFallback>
                                        </Avatar>
                                        <div>
                                            <Button variant="outline" className="gap-2">
                                                <Camera className="h-4 w-4" />
                                                Alterar Foto
                                            </Button>
                                            <p className="text-sm text-muted-foreground mt-2">
                                                JPG, PNG ou GIF. Tamanho máximo: 2MB
                                            </p>
                                        </div>
                                    </div>

                                    <Separator />

                                    <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                                        <div className="space-y-2">
                                            <Label htmlFor="name">Nome Completo</Label>
                                            <Input
                                                id="name"
                                                value={profileData.name}
                                                onChange={(e) => handleProfileUpdate('name', e.target.value)}
                                            />
                                        </div>

                                        <div className="space-y-2">
                                            <Label htmlFor="email">Email</Label>
                                            <Input
                                                id="email"
                                                type="email"
                                                value={profileData.email}
                                                onChange={(e) => handleProfileUpdate('email', e.target.value)}
                                            />
                                        </div>

                                        <div className="space-y-2">
                                            <Label htmlFor="phone">Telefone</Label>
                                            <Input
                                                id="phone"
                                                value={profileData.phone}
                                                onChange={(e) => handleProfileUpdate('phone', e.target.value)}
                                            />
                                        </div>

                                        <div className="space-y-2">
                                            <Label htmlFor="location">Localização</Label>
                                            <Input
                                                id="location"
                                                value={profileData.location}
                                                onChange={(e) => handleProfileUpdate('location', e.target.value)}
                                            />
                                        </div>
                                    </div>

                                    <div className="space-y-2">
                                        <Label htmlFor="bio">Sobre Você</Label>
                                        <Textarea
                                            id="bio"
                                            placeholder="Conte um pouco sobre você e o que procura..."
                                            value={profileData.bio}
                                            onChange={(e) => handleProfileUpdate('bio', e.target.value)}
                                            className="min-h-[100px]"
                                        />
                                    </div>

                                    <div className="flex justify-end">
                                        <Button className="bg-terracotta hover:bg-terracotta/90 gap-2">
                                            <Save className="h-4 w-4" />
                                            Salvar Alterações
                                        </Button>
                                    </div>
                                </CardContent>
                            </Card>
                        </TabsContent>

                        <TabsContent value="notifications" className="space-y-6">
                            <Card>
                                <CardHeader>
                                    <CardTitle className="flex items-center gap-2">
                                        <Bell className="h-5 w-5" />
                                        Preferências de Notificação
                                    </CardTitle>
                                    <CardDescription>
                                        Configure como você deseja receber notificações
                                    </CardDescription>
                                </CardHeader>
                                <CardContent className="space-y-6">
                                    <div className="space-y-4">
                                        <div className="flex items-center justify-between">
                                            <div className="space-y-0.5">
                                                <Label>Notificações por Email</Label>
                                                <p className="text-sm text-muted-foreground">
                                                    Receba atualizações importantes por email
                                                </p>
                                            </div>
                                            <Switch
                                                checked={preferences.emailNotifications}
                                                onCheckedChange={() => handlePreferenceToggle('emailNotifications')}
                                            />
                                        </div>

                                        <div className="flex items-center justify-between">
                                            <div className="space-y-0.5">
                                                <Label>Notificações por SMS</Label>
                                                <p className="text-sm text-muted-foreground">
                                                    Receba alertas urgentes por mensagem de texto
                                                </p>
                                            </div>
                                            <Switch
                                                checked={preferences.smsNotifications}
                                                onCheckedChange={() => handlePreferenceToggle('smsNotifications')}
                                            />
                                        </div>

                                        <div className="flex items-center justify-between">
                                            <div className="space-y-0.5">
                                                <Label>Emails de Marketing</Label>
                                                <p className="text-sm text-muted-foreground">
                                                    Receba dicas e ofertas especiais
                                                </p>
                                            </div>
                                            <Switch
                                                checked={preferences.marketingEmails}
                                                onCheckedChange={() => handlePreferenceToggle('marketingEmails')}
                                            />
                                        </div>

                                        <div className="flex items-center justify-between">
                                            <div className="space-y-0.5">
                                                <Label>Alertas de Novas Propriedades</Label>
                                                <p className="text-sm text-muted-foreground">
                                                    Seja notificado quando novos imóveis corresponderem ao seu perfil
                                                </p>
                                            </div>
                                            <Switch
                                                checked={preferences.newPropertyAlerts}
                                                onCheckedChange={() => handlePreferenceToggle('newPropertyAlerts')}
                                            />
                                        </div>
                                    </div>

                                    <div className="flex justify-end">
                                        <Button className="bg-terracotta hover:bg-terracotta/90 gap-2">
                                            <Save className="h-4 w-4" />
                                            Salvar Preferências
                                        </Button>
                                    </div>
                                </CardContent>
                            </Card>
                        </TabsContent>

                        <TabsContent value="security" className="space-y-6">
                            <Card>
                                <CardHeader>
                                    <CardTitle className="flex items-center gap-2">
                                        <Shield className="h-5 w-5" />
                                        Segurança da Conta
                                    </CardTitle>
                                    <CardDescription>
                                        Gerencie a segurança da sua conta
                                    </CardDescription>
                                </CardHeader>
                                <CardContent className="space-y-6">
                                    <div className="space-y-4">
                                        <div>
                                            <Label className="text-base font-medium">Alterar Senha</Label>
                                            <div className="grid gap-3 mt-3">
                                                <div>
                                                    <Label htmlFor="current-password">Senha Atual</Label>
                                                    <Input id="current-password" type="password" />
                                                </div>
                                                <div>
                                                    <Label htmlFor="new-password">Nova Senha</Label>
                                                    <Input id="new-password" type="password" />
                                                </div>
                                                <div>
                                                    <Label htmlFor="confirm-password">Confirmar Nova Senha</Label>
                                                    <Input id="confirm-password" type="password" />
                                                </div>
                                            </div>
                                        </div>

                                        <Separator />

                                        <div>
                                            <Label className="text-base font-medium">Verificação em Duas Etapas</Label>
                                            <div className="flex items-center justify-between mt-3">
                                                <div>
                                                    <p className="text-sm text-muted-foreground">
                                                        Adicione uma camada extra de segurança à sua conta
                                                    </p>
                                                    <Badge variant="secondary" className="mt-1">Desabilitado</Badge>
                                                </div>
                                                <Button variant="outline">Configurar</Button>
                                            </div>
                                        </div>
                                    </div>

                                    <div className="flex justify-end">
                                        <Button className="bg-terracotta hover:bg-terracotta/90 gap-2">
                                            <Save className="h-4 w-4" />
                                            Atualizar Segurança
                                        </Button>
                                    </div>
                                </CardContent>
                            </Card>
                        </TabsContent>

                        <TabsContent value="billing" className="space-y-6">
                            <Card>
                                <CardHeader>
                                    <CardTitle className="flex items-center gap-2">
                                        <CreditCard className="h-5 w-5" />
                                        Informações de Pagamento
                                    </CardTitle>
                                    <CardDescription>
                                        Gerencie seus métodos de pagamento e faturas
                                    </CardDescription>
                                </CardHeader>
                                <CardContent className="space-y-6">
                                    <div>
                                        <Label className="text-base font-medium">Plano Atual</Label>
                                        <div className="mt-3 p-4 border rounded-lg">
                                            <div className="flex items-center justify-between">
                                                <div>
                                                    <h3 className="font-medium">Plano Gratuito</h3>
                                                    <p className="text-sm text-muted-foreground">
                                                        Acesso básico à plataforma
                                                    </p>
                                                </div>
                                                <Button variant="outline">Fazer Upgrade</Button>
                                            </div>
                                        </div>
                                    </div>

                                    <Separator />

                                    <div>
                                        <Label className="text-base font-medium">Métodos de Pagamento</Label>
                                        <div className="mt-3">
                                            <p className="text-sm text-muted-foreground mb-4">
                                                Nenhum método de pagamento cadastrado
                                            </p>
                                            <Button variant="outline">
                                                <CreditCard className="h-4 w-4 mr-2" />
                                                Adicionar Cartão
                                            </Button>
                                        </div>
                                    </div>
                                </CardContent>
                            </Card>
                        </TabsContent>
                    </Tabs>
                </div>
            </div>
        </div>
    );
};

export default UserSettingsPage;
