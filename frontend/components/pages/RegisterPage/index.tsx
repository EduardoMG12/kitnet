import React from "react";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group";
import { Checkbox } from "@/components/ui/checkbox";
import MainLayout from "@/components/layout/MainLayout";
import { Home } from "lucide-react";
import Link from "next/link";

const RegisterPage = () => {
    return (
        <MainLayout hideFooter>
            <div className="min-h-screen flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8 bg-[#f9f4e8]">
                <div className="w-full max-w-md space-y-8">
                    <div className="text-center">
                        <Link href="/" className="flex items-center justify-center gap-2">
                            <Home className="h-6 w-6 text-[#e56b4e]" />
                            <span className="text-xl font-heading font-semibold">Casa Alegria</span>
                        </Link>
                        <h2 className="mt-6 text-3xl font-heading font-bold text-[#2c3e50]">
                            Crie sua conta
                        </h2>
                        <p className="mt-2 text-sm text-muted-foreground">
                            Já tem uma conta?{" "}
                            <Link href="/login" className="text-[#e56b4e] hover:underline">
                                Entrar
                            </Link>
                        </p>
                    </div>

                    <div className="bg-white p-8 rounded-lg shadow-md">
                        <form className="space-y-6">
                            <div>
                                <Label>Você é:</Label>
                                <RadioGroup defaultValue="tenant" className="grid grid-cols-2 gap-4 mt-2">
                                    <div>
                                        <RadioGroupItem value="tenant" id="tenant" className="peer sr-only" />
                                        <Label
                                            htmlFor="tenant"
                                            className="flex flex-col items-center justify-between rounded-md border-2 border-muted bg-transparent p-4 hover:bg-muted hover:text-accent-foreground peer-data-[state=checked]:border-[#e56b4e] [&:has([data-state=checked])]:border-[#e56b4e]"
                                        >
                                            <span className="font-medium">Inquilino</span>
                                            <span className="text-xs text-muted-foreground">
                                                Estou procurando um imóvel para alugar
                                            </span>
                                        </Label>
                                    </div>
                                    <div>
                                        <RadioGroupItem value="landlord" id="landlord" className="peer sr-only" />
                                        <Label
                                            htmlFor="landlord"
                                            className="flex flex-col items-center justify-between rounded-md border-2 border-muted bg-transparent p-4 hover:bg-muted hover:text-accent-foreground peer-data-[state=checked]:border-[#e56b4e] [&:has([data-state=checked])]:border-[#e56b4e]"
                                        >
                                            <span className="font-medium">Proprietário</span>
                                            <span className="text-xs text-muted-foreground">
                                                Quero anunciar meu imóvel
                                            </span>
                                        </Label>
                                    </div>
                                </RadioGroup>
                            </div>

                            <div className="grid grid-cols-2 gap-4">
                                <div>
                                    <Label htmlFor="first-name">Nome</Label>
                                    <Input
                                        id="first-name"
                                        type="text"
                                        placeholder="João"
                                        required
                                        className="mt-1"
                                    />
                                </div>
                                <div>
                                    <Label htmlFor="last-name">Sobrenome</Label>
                                    <Input
                                        id="last-name"
                                        type="text"
                                        placeholder="Silva"
                                        required
                                        className="mt-1"
                                    />
                                </div>
                            </div>

                            <div>
                                <Label htmlFor="email">Email</Label>
                                <Input
                                    id="email"
                                    type="email"
                                    placeholder="seu@email.com"
                                    autoComplete="email"
                                    required
                                    className="mt-1"
                                />
                            </div>

                            <div>
                                <Label htmlFor="phone">Telefone</Label>
                                <Input
                                    id="phone"
                                    type="tel"
                                    placeholder="(11) 99999-9999"
                                    required
                                    className="mt-1"
                                />
                            </div>

                            <div>
                                <Label htmlFor="password">Senha</Label>
                                <Input
                                    id="password"
                                    type="password"
                                    placeholder="••••••••"
                                    autoComplete="new-password"
                                    required
                                    className="mt-1"
                                />
                            </div>

                            <div>
                                <Label htmlFor="password-confirm">Confirme sua senha</Label>
                                <Input
                                    id="password-confirm"
                                    type="password"
                                    placeholder="••••••••"
                                    autoComplete="new-password"
                                    required
                                    className="mt-1"
                                />
                            </div>

                            <div className="flex items-start space-x-2">
                                <Checkbox id="terms" />
                                <div className="grid gap-1.5 leading-none">
                                    <label
                                        htmlFor="terms"
                                        className="text-sm text-muted-foreground leading-relaxed"
                                    >
                                        Ao criar uma conta, você concorda com nossos{" "}
                                        <Link href="/terms" className="text-[#e56b4e] hover:underline">
                                            Termos de Uso
                                        </Link>{" "}
                                        e{" "}
                                        <Link href="/privacy" className="text-[#e56b4e] hover:underline">
                                            Política de Privacidade
                                        </Link>
                                    </label>
                                </div>
                            </div>

                            <Button type="submit" className="w-full bg-[#e56b4e] hover:bg-[#e56b4e]/90">
                                Criar conta
                            </Button>
                        </form>

                        <div className="mt-6">
                            <div className="relative">
                                <div className="absolute inset-0 flex items-center">
                                    <div className="w-full border-t border-gray-300" />
                                </div>
                                <div className="relative flex justify-center text-sm">
                                    <span className="bg-white px-2 text-muted-foreground">
                                        Ou continue com
                                    </span>
                                </div>
                            </div>

                            <div className="mt-6 grid grid-cols-2 gap-3">
                                <Button variant="outline" type="button" className="w-full">
                                    Google
                                </Button>
                                <Button variant="outline" type="button" className="w-full">
                                    Facebook
                                </Button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </MainLayout>
    );
};

export default RegisterPage;
