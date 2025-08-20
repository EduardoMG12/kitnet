'use client';

import Link from 'next/link';
import { useEffect, useState } from 'react';
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { Shield, ShieldX, Lock, ArrowLeft, HomeIcon, Eye, EyeOff } from "lucide-react";

const AccessDeniedInteractiveContent: React.FC = () => {
    const [shieldState, setShieldState] = useState<number>(0);
    const [showPassword, setShowPassword] = useState<boolean>(false);

    useEffect(() => {
        console.error("403 Error: Access Denied - Insufficient Permissions");

        const interval = setInterval(() => {
            setShieldState((prev) => (prev < 2 ? prev + 1 : 0));
        }, 1500);

        return () => clearInterval(interval);
    }, []);

    const getShieldIcon = () => {
        switch (shieldState) {
            case 0:
                return <Shield className="h-20 w-20 text-red-500 animate-pulse" />;
            case 1:
                return <ShieldX className="h-20 w-20 text-orange-500 animate-bounce" />;
            default:
                return <Lock className="h-20 w-20 text-red-600 animate-spin" />;
        }
    };

    return (
        <div className="h-screen flex flex-col items-center justify-center bg-gradient-to-b from-red-50 to-red-100 dark:from-gray-900 dark:to-red-900/20 p-4">
            <Card className="max-w-lg w-full p-6 bg-white dark:bg-gray-800 shadow-xl border-red-200 dark:border-red-700">
                <div className="flex flex-col items-center text-center">
                    <div className="mb-4">
                        {getShieldIcon()}
                    </div>

                    <h1 className="text-6xl font-bold mb-2 text-red-600 dark:text-red-400">403</h1>
                    <h2 className="text-2xl font-semibold mb-4 text-gray-700 dark:text-gray-300">
                        Acesso Proibido! 🚫
                    </h2>

                    <p className="text-gray-600 dark:text-gray-400 mb-6">
                        Opa! Parece que você está tentando entrar numa área VIP sem convite.
                        É como tentar entrar numa festa exclusiva de pijama usando smoking!
                    </p>

                    <div className="w-full mb-6 p-4 bg-red-50 dark:bg-red-900/20 rounded-lg border border-red-200 dark:border-red-700">
                        <p className="text-sm text-red-700 dark:text-red-300 mb-3">
                            🔐 Tentativa de acesso bloqueada!
                        </p>
                        <div className="bg-gray-100 dark:bg-gray-700 p-3 rounded font-mono text-xs">
                            <div className="flex items-center justify-between">
                                <span>Senha secreta: </span>
                                <Button
                                    variant="ghost"
                                    size="sm"
                                    onClick={() => setShowPassword(!showPassword)}
                                    className="h-6 w-6 p-0"
                                >
                                    {showPassword ? <EyeOff className="h-3 w-3" /> : <Eye className="h-3 w-3" />}
                                </Button>
                            </div>
                            <span className="text-red-500">
                                {showPassword ? "admin123" : "••••••••"}
                            </span>
                            <div className="text-xs text-gray-500 mt-1">
                                (Psiu... não funciona mesmo! 😉)
                            </div>
                        </div>
                    </div>

                    <div className="space-y-4 w-full">
                        <div className="p-4 bg-blue-50 dark:bg-blue-900/20 rounded-lg">
                            <p className="text-sm text-gray-600 dark:text-gray-300 mb-3">
                                💡 Dicas para conseguir acesso:
                            </p>
                            <ul className="text-xs text-gray-500 dark:text-gray-400 space-y-1 text-left">
                                <li>• Faça login com uma conta autorizada</li>
                                <li>• Peça permissão ao administrador</li>
                                <li>• Ou tente suborno com cookies 🍪</li>
                            </ul>
                        </div>

                        <div className="flex flex-col sm:flex-row gap-3 justify-center">
                            <Button
                                onClick={() => window.history.back()}
                                variant="outline"
                                className="flex-1"
                            >
                                <ArrowLeft className="h-4 w-4 mr-2" />
                                Voltar
                            </Button>
                            <Link href="/login" className="flex-1">
                                <Button variant="secondary" className="w-full">
                                    <Lock className="h-4 w-4 mr-2" />
                                    Fazer Login
                                </Button>
                            </Link>
                            <Link href="/" className="flex-1">
                                <Button className="w-full bg-red-500 hover:bg-red-600">
                                    <HomeIcon className="h-4 w-4 mr-2" />
                                    Início
                                </Button>
                            </Link>
                        </div>
                    </div>
                </div>
            </Card>

            <div className="mt-8 text-center">
                <p className="text-gray-600 dark:text-gray-400 text-sm">
                    Se você acha que deveria ter acesso, entre em contato com o suporte!
                </p>
            </div>
        </div>
    );
};

export default AccessDeniedInteractiveContent;