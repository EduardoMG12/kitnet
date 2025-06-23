# frontend/Dockerfile

# --- STAGE 1: Install dependencies and build the application ---
FROM node:23.8.0-alpine AS builder

WORKDIR /app

# Enable Corepack for pnpm (still needed if pnpm isn't in global PATH by default in the image)
RUN corepack enable

# Copy lock file and package.json
COPY package.json pnpm-lock.yaml ./

# Install dependencies using pnpm
RUN pnpm install --frozen-lockfile

# Copy the rest of the source code
COPY . .

# Build the application (for production image)
RUN pnpm build

# --- STAGE 2: Serve the application ---
FROM node:23.8.0-alpine AS runner

WORKDIR /app

# Copy only the necessary build outputs and static assets
COPY --from=builder /app/.next ./.next
COPY --from=builder /app/node_modules ./node_modules
    # Need node_modules for 'next' executable
COPY --from=builder /app/public ./public
COPY --from=builder /app/package.json ./package.json
    # Needed for 'next start' to find script

ENV PORT 3000
EXPOSE 3000

# *** CRITICAL CHANGE HERE ***
# Directly call the 'next' executable from node_modules/.bin
# This is generally the most reliable way to run Next.js inside Docker
CMD ["node_modules/.bin/next", "start", "-p", "3000"]
# For production image