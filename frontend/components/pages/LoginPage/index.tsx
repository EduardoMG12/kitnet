import React from "react";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Checkbox } from "@/components/ui/checkbox";
import MainLayout from "@/components/layout/MainLayout";
import { Home } from "lucide-react";
import Link from "next/link";

const LoginPage = () => {
    return (
        <MainLayout hideFooter>
            <div className="min-h-screen flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8 bg-[#f9f4e8]">
                <div className="w-full max-w-md space-y-8">
                    <div className="text-center">
                        <Link href="/" className="flex items-center justify-center gap-2">
                            <Home className="h-6 w-6 text-[#e56b4e]" />
                            <span className="text-xl font-heading font-semibold">KitNet</span>
                        </Link>
                        <h2 className="mt-6 text-3xl font-heading font-bold text-[#2c3e50]">
                            Bem-vindo de volta
                        </h2>
                        <p className="mt-2 text-sm text-muted-foreground">
                            Não tem uma conta?{" "}
                            <Link href="/register" className="text-[#e56b4e] hover:underline">
                                Registre-se
                            </Link>
                        </p>
                    </div>

                    <div className="bg-white p-8 rounded-lg shadow-md">
                        <form className="space-y-6">
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
                                <div className="flex justify-between items-center">
                                    <Label htmlFor="password">Senha</Label>
                                    <Link
                                        href="/forgot-password"
                                        className="text-sm text-[#e56b4e] hover:underline"
                                    >
                                        Esqueceu a senha?
                                    </Link>
                                </div>
                                <Input
                                    id="password"
                                    type="password"
                                    placeholder="••••••••"
                                    autoComplete="current-password"
                                    required
                                    className="mt-1"
                                />
                            </div>

                            <div className="flex items-center space-x-2">
                                <Checkbox id="remember" />
                                <label
                                    htmlFor="remember"
                                    className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
                                >
                                    Lembrar de mim
                                </label>
                            </div>

                            <Button type="submit" className="w-full bg-[#e56b4e] hover:bg-[#e56b4e]/90">
                                Entrar
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

export default LoginPage;